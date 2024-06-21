import yaml

def read_yaml(file_path):
    try:
        with open(file_path, 'r') as file:
            data = yaml.safe_load(file)
            return data
    except Exception as e:
        print(f"An error occurred while reading the YAML file: {e}")
        return None

def get_config_by_name(name, config):
    for list in config:
        if list['name'] == name:
            return list
    return None