import os
import argparse
import re
from config import *
from markdown_util import *
from file_util import *

## global var
root_dir = "docs"
commit_id = ""
commit_dir_path = ""
config_path = ".github/config/gh-pages.yml"
global_config = {}

latest_pattern = r"### Latest\n(.+?)(?=\n- - -)"
other_pattern = r"### Other\n(.+?)(?=\n- - -)"



def init_develop_markdown():
    develop_config = get_config_by_name("develop", global_config.get("main"))
    description = develop_config.get("description")
    content = f"## develop\n### description\n{description}\n\n"
    content += "### Latest\n<!-- Latest -->\n\n"
    content += "### Other\n<!-- Other --> \n\n"

    create_markdown_file('/'.join([root_dir,"develop","index.md"]), content)

def update_develop_markdown(text):
    develop_markdown_path = '/'.join([root_dir,"develop" ,"index.md"])
    if not os.path.exists(develop_markdown_path):
        init_develop_markdown()

    latest_line = find_line_number(develop_markdown_path,"## Latest")
    latest_end_line =  find_line_number(develop_markdown_path,"<!-- Latest -->", start_line=latest_line)

    other_line = find_line_number(develop_markdown_path,"### Other")

    if latest_end_line - latest_line == 1 :
        # 현재 Latest가 비어있는 경우
        add_line_to_file(develop_markdown_path, latest_line +1 , text)
    else:
        # Latest가 존재하는 경우
        orign_text = replace_line_in_file(develop_markdown_path,latest_line +1, text)
        add_line_to_file(develop_markdown_path, other_line +1 , orign_text)

def get_exist_latest():
    exist_content = read_markdown('/'.join([root_dir, "index.md"]))

    latest_match = re.search(latest_pattern, exist_content, re.DOTALL)


    if not latest_match:
        return None

    return latest_match.group(1).strip()

def get_exist_other():
    exist_content = read_markdown('/'.join([root_dir, "index.md"]))

    other_match = re.search(other_pattern, exist_content, re.DOTALL)

    if not other_match:
        return None

    return other_match.group(1).strip()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        prog="Generate docs resource",
        description="Generate docs resource by commit name",
        epilog="github.com/can019"
    )

    ## init set up
    parser.add_argument("-i", "--id", type=str, help="Commit id", required=True)
    args = parser.parse_args()

    commit_id = args.id
    commit_dir_path = '/'.join([root_dir, "develop", args.id])

    global_config = read_yaml(config_path)


    create_folder(commit_dir_path)
    create_commit_id_markdown('/'.join([commit_dir_path, "index.md"]), global_config.get('list'))


    update_develop_markdown(create_markdown_list(create_markdown_link_text(commit_id, f"{commit_id}/index.md")))

