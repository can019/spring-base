import argparse
import yaml

# 명령줄 인자 처리
parser = argparse.ArgumentParser(description='Add a new URL to the Swagger config file.')
parser.add_argument('-u', '--url', required=True, help='The URL of the API spec file')
parser.add_argument('-n', '--name', required=True, help='The name of the API')
parser.add_argument('-f', '--file', required=True, help='The path to the config YAML file')
args = parser.parse_args()

# 입력받은 config 파일 경로
config_file_path = args.file

# 기존 config.yaml 파일 읽기
try:
    with open(config_file_path, 'r') as file:
        config = yaml.safe_load(file)
except FileNotFoundError:
    print(f"File not found: {config_file_path}")
    exit(1)

# urls 배열이 존재하지 않으면 생성
if 'urls' not in config:
    config['urls'] = []

# 새로운 엔트리를 urls 배열의 맨 앞에 추가
new_entry = {'url': args.url, 'name': args.name}
config['urls'].insert(0, new_entry)

# 변경된 설정을 다시 config.yaml 파일에 쓰기
with open(config_file_path, 'w') as file:
    yaml.safe_dump(config, file, default_flow_style=False)

print(f"Added {args.url} with name {args.name} to {config_file_path}")
