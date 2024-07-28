import argparse
import os

def main(file, content):
    if not os.path.exists(file):
        print(f"Error: File {file} does not exist.")
        exit(1)

    with open(file, 'r') as f:
        lines = f.readlines()

    latest_start = None
    latest_end = None
    other_start = None
    other_end = None

    # Find the indices of the markers
    for i, line in enumerate(lines):
        if '### Latest' in line:
            latest_start = i
        if '<!-- Latest -->' in line:
            latest_end = i
        if '### Other' in line:
            other_start = i
        if '<!-- Other -->' in line:
            other_end = i

    if latest_start is None or latest_end is None or other_start is None or other_end is None:
        print("Error: Markers not found in the file.")
        exit(1)

    # Extract the content of the Latest section
    latest_content = lines[latest_start + 1:latest_end]

    # Prepare the new content for the Latest section
    new_latest_content = [content + '\n']

    # Update the file content
    updated_lines = (
            lines[:latest_start + 1] +
            new_latest_content +
            lines[latest_end:other_start + 1] +
            latest_content +
            lines[other_start + 1:]
    )

    with open(file, 'w') as f:
        f.writelines(updated_lines)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Update markdown sections.')
    parser.add_argument('-f', '--file', required=True, help='The file to process')
    parser.add_argument('-c', '--content', required=True, help='The content to add')
    args = parser.parse_args()

    main(args.file, args.content)
