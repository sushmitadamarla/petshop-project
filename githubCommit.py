import os
import subprocess

repo_path = r"C:\Users\yetes\OneDrive\Desktop\petshop-project"
os.chdir(repo_path)

# Switch to transaction-module branch
subprocess.run(["git", "checkout", "transaction-module"])

# Example commit messages for transaction work
commit_messages = [
    "Create Transaction entity and repository",
    "Implement TransactionDTO with validation",
    "Add service layer for transaction processing",
    "Integrate transaction module with payment gateway",
    "Fix bug in transaction rollback logic",
    "Refactor transaction controller for clarity",
    "Add unit tests for transaction service",
    "Update database schema with transaction table",
    "Improve error handling in transaction API",
    "Document transaction workflow in README"
]

for msg in commit_messages:
    filename = "dummy.txt"
    with open(filename, "a") as f:
        f.write(f"{msg}\n")

    subprocess.run(["git", "add", filename])
    subprocess.run(["git", "commit", "-m", msg])

# Push commits to transaction-module branch
subprocess.run(["git", "push", "origin", "transaction-module"])

print("✅ Commits created and pushed to transaction-module branch!")