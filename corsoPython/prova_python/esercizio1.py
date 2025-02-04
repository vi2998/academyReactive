def read_input_file(file_path):
    """Reads a text file and converts each line to a dictionary."""
    data_list = []
    with open(file_path, 'r') as file:
        lines = file.readlines()

        # Get header keys from the first line
        keys = lines[0].strip().split(',')

        # Process each remaining line into a dictionary
        for line in lines[1:]:
            values = line.strip().split(',')
            entry = dict(zip(keys, values)) # entry -> dizionario di chiavi-valore
            entry["age"] = int(entry["age"])
            entry["salary"] = int(entry["salary"])
            data_list.append(entry)
    return data_list


def filter_employees(data_list):
    """Filter out employees with a salary less than 6000."""
    return [entry for entry in data_list if entry["salary"] >= 6000]


def write_output_file(file_path, data_list):
    """Write the filtered data to an output file."""
    if not data_list:
        print("No data to write.")
        return

    # Write header row
    keys = data_list[0].keys()
    with open(file_path, 'w') as file:
        file.write(','.join(keys) + '\n')
        for entry in data_list:
            values = [str(entry[key]) for key in keys]
            file.write(','.join(values) + '\n')


def main():
    input_file = 'data.txt'
    output_file = 'filtered_data.txt'

    # Read input file
    data_list = read_input_file(input_file)
    print("Original Data:", data_list)

    # Filter employees
    filtered_data = filter_employees(data_list)
    print("Filtered Data:", filtered_data)

    # Write output file
    write_output_file(output_file, filtered_data)
    print(f"Filtered data written to '{output_file}'")

if __name__ == "__main__":
    main()