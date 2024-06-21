import os
import argparse
import re
from config import *
from markdown_util import *
from file_util import *

## global var
root_dir = "docs"
commit_id = ""

identifier_dir_path = ""
config_path = ".github/config/gh-pages.yml"
global_config = {}
release_version=""
mode = ""
latest_pattern = r"### Latest\n(.+?)(?=\n- - -)"
other_pattern = r"### Other\n(.+?)(?=\n- - -)"



def init_second_root_markdown():
    name_config = get_config_by_name(mode, global_config.get("main"))
    print(name_config)
    description = name_config.get("description")
    content = f"## {mode}\n### description\n{description}\n\n"
    content += "### Latest\n<!-- Latest -->\n\n"
    content += "### Other\n<!-- Other --> \n\n"

    create_markdown_file('/'.join([root_dir,mode ,"index.md"]), content)

def update_second_root_markdown(text):
    second_root_markdown_path = '/'.join([root_dir, mode,"index.md"])
    if not os.path.exists(second_root_markdown_path):
        init_second_root_markdown()

    latest_line = find_line_number(second_root_markdown_path,"## Latest")
    latest_end_line =  find_line_number(second_root_markdown_path,"<!-- Latest -->", start_line=latest_line)

    other_line = find_line_number(second_root_markdown_path,"### Other")

    if latest_end_line - latest_line == 1 :
        # 현재 Latest가 비어있는 경우
        add_line_to_file(second_root_markdown_path, latest_line +1 , text)
    else:
        # Latest가 존재하는 경우
        orign_text = replace_line_in_file(second_root_markdown_path,latest_line +1, text)
        add_line_to_file(second_root_markdown_path, other_line +1 , orign_text)

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
    parser.add_argument("-t", "--type", type=str, choices=["release", "develop"], help="Commit id (release or develop)", required=True)
    parser.add_argument("-rv", "--rversion", type=str, help="Release version", required=False)
    args = parser.parse_args()

    mode = args.type
    commit_id = args.id # commit id or release tag

    identifier_link = ""
    print(release_version)
    if mode == "release":
        release_version = args.rversion
        if release_version is None:
            raise ValueError("Release version이 존재하지 않습니다.")
        identifier_dir_path = '/'.join([root_dir, mode, release_version]) # docs/${mode}/${identifier}
        identifier_link = create_markdown_link_text(release_version, f"{identifier_dir_path}/index.md")   # [release_version](release_version/index.md)
    elif mode == "develop":
        identifier_dir_path = '/'.join([root_dir, mode, commit_id]) # docs/${mode}/${identifier}
        identifier_link = create_markdown_link_text(commit_id, f"{identifier_dir_path}/index.md")   # [commit_id]($identifier/index.md)
    else:
        raise ValueError("Type이 올바르지 않습니다")
    global_config = read_yaml(config_path)

    create_folder(identifier_dir_path)
    create_id_markdown('/'.join([identifier_dir_path, "index.md"]), global_config.get('list'))

    update_second_root_markdown(create_markdown_list(identifier_link))

