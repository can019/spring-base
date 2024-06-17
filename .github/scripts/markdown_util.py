def create_markdown_file(file_path, content):
    try:
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(content)
        print(f"Markdown file '{file_path}' created successfully.")
    except Exception as e:
        print(f"An error occurred while creating the file: {e}")

def create_markdown_link_text(description, link):
    return f"[{description}]({link})"

def create_markdown_list(text):
    return f"- {text}"

def read_markdown(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            markdown_content = file.read()
            return markdown_content
    except FileNotFoundError:
        print(f"Error: The file '{file_path}' does not exist.")
    except Exception as e:
        print(f"Error: {e}")

def read_markdown_as_line(file_path):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            markdown_content = file.readlines()
            return markdown_content
    except FileNotFoundError:
        print(f"Error: The file '{file_path}' does not exist.")
    except Exception as e:
        print(f"Error: {e}")

def create_commit_id_markdown(file_path, list):
    header = "## List"
    main = ""

    for target in list:
        main+="\n".join([f"### {target}", create_markdown_link_text(target, f"{target}/index.md"),"","---", ""])

    content = "\n".join([header, main])

    create_markdown_file(file_path, content)