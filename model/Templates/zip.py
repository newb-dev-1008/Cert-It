from zipfile import ZipFile 
import os 
  
def get_all_file_paths(directory): 
  
    file_paths = [] 
  
    for root, directories, files in os.walk(directory): 

        for filename in files: 
             
            filepath = os.path.join(root, filename) 

            file_paths.append(filepath) 
  
     
    return file_paths         
  
def main(): 

    directory = r'C:\Users\ROSHAN\Certificate Maker\Images\Snapshots'
   
    file_paths = get_all_file_paths(directory) 
  
    for file_name in file_paths:

        if file_name.endswith('.png'): 

            with ZipFile('Certificates.zip','w') as zip: 

                for file in file_paths:

                    if file.endswith('.png'):

                        zip.write(file)         
  
if __name__ == "__main__": 
    main() 