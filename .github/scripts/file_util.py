import os

def create_folder(folder_path):
    try:
        # os.makedirs를 사용하여 중첩된 폴더까지 생성 가능
        os.makedirs(folder_path, exist_ok=True)
        print(f"Folder '{folder_path}' created successfully.")
    except Exception as e:
        print(f"An error occurred while creating the folder: {e}")

def replace_line_in_file(file_path, line_number, new_content):
    try:
        original_content = None
        with open(file_path, 'r', encoding='utf-8') as file:
            lines = file.readlines()

        # line_number가 유효한지 확인
        if 0 < line_number <= len(lines):
            # 변경 전 내용을 기록하고 수정
            original_content = lines[line_number - 1].strip()
            lines[line_number - 1] = new_content + '\n'

            # 파일을 쓰기 모드로 열어서 수정된 내용을 씀
            with open(file_path, 'w', encoding='utf-8') as file:
                file.writelines(lines)
            print(f"Successfully replaced content in line {line_number}.")
        else:
            print(f"Error: Line number {line_number} is out of range.")

        return original_content

    except FileNotFoundError:
        print(f"Error: The file '{file_path}' does not exist.")
    except Exception as e:
        print(f"Error: {e}")

def add_line_to_file(file_path, line_number, new_content):
    try:
        with open(file_path, 'r', encoding='utf-8') as file:
            lines = file.readlines()

        # line_number가 유효한지 확인
        if 0 < line_number <= len(lines) + 1:
            # 새로운 내용을 추가할 위치에 삽입
            lines.insert(line_number - 1, new_content + '\n')

            # 파일을 쓰기 모드로 열어서 새로운 내용을 쓰기
            with open(file_path, 'w', encoding='utf-8') as file:
                file.writelines(lines)
            print(f"Successfully added new content to line {line_number}.")
        else:
            print(f"Error: Line number {line_number} is out of range.")
    except FileNotFoundError:
        print(f"Error: The file '{file_path}' does not exist.")
    except Exception as e:
        print(f"Error: {e}")

def find_line_number(markdown_file, target_string, start_line=0):
    try:
        with open(markdown_file, 'r', encoding='utf-8') as file:
            for line_num, line in enumerate(file, start=1):
                if start_line < line_num and target_string in line:
                    return line_num
        # 타겟 문자열을 찾지 못한 경우
        return None
    except FileNotFoundError:
        print(f"Error: The file '{markdown_file}' does not exist.")
    except Exception as e:
        print(f"Error: {e}")