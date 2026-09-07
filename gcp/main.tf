terraform {
  required_version = ">= 1.0.0"
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }

  # Same state bucket as the shared IaC repo (ahun-cloud-env), different prefix.
  # One prefix per service so the states never collide.
  backend "gcs" {
    bucket = "casa-ahun-tfstate"
    prefix = "terraform/state/cloud-run/ahun-members-service"
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

locals {
  # Plaintext env vars. Empty values are dropped so we never set an env var to "".
  # GOOGLE_CREDENTIALS == "DEFAULT_GCP" is the sentinel for "use the runtime SA /
  # ADC instead of an explicit key" -> also dropped.
  base_env_vars = {
    SPRING_DATASOURCE_URL      = var.spring_datasource_url
    SPRING_DATASOURCE_USERNAME = var.spring_datasource_username
    SPRING_DATASOURCE_PASSWORD = var.spring_datasource_password
    TELEGRAM_CHAT_ID           = var.telegram_chat_id
    GOOGLE_CREDENTIALS         = var.google_credentials == "DEFAULT_GCP" ? "" : replace(var.google_credentials, "\n", "")
  }

  env_vars = merge(
    { for k, v in local.base_env_vars : k => v if v != "" },
    var.extra_env_vars,
  )

  # Telegram bot token comes from Secret Manager (managed by the shared IaC repo).
  secret_env_vars = var.bot_token_secret_id != "" ? {
    TELEGRAM_BOT_TOKEN = var.bot_token_secret_id
  } : {}
}

module "cloud_run" {
  source = "./modules/cloud_run"

  project_id              = var.project_id
  region                  = var.region
  service_name            = "ahun-members-service"
  env_vars                = local.env_vars
  secret_env_vars         = local.secret_env_vars
  github_actions_sa_email = var.github_actions_sa_email

  scheduler_jobs = {
    "daily-bday" = {
      description = "Triggers the daily birthday notification routine"
      schedule    = "0 8 * * *"
      time_zone   = "America/Sao_Paulo"
      uri_path    = "/api/messaging/send"
      http_method = "POST"
      body        = "{\"daily\":true}"
    }
    "monthly-notif" = {
      description = "Triggers the monthly birthday digest routine"
      schedule    = "0 9 1 * *"
      time_zone   = "America/Sao_Paulo"
      uri_path    = "/api/messaging/send"
      http_method = "POST"
      body        = "{\"daily\":false}"
    }
  }
}
