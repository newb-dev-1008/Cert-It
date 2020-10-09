import csv
import requests
from openpyxl import Workbook

def main(n):
  response = requests.get("http://names.drycodes.com/"+str(n)+"?nameOptions=boy_names")
  names = response.json()

  with open('mock_data.csv', 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(["Name" , "Company Name" , "Certificate", "Certifier1" ,"CertifierPosition1", "Certifier2" , "CertifierPosition2", "Date"])
    for i in range(len(names)):
      name = names[i].replace("_", " ")
      writer.writerow([name, "GlobalCert", "Participation", "Ahmed Mawia", "Chief Technical Officer", "Rohan Mathur", "Chief Executive Officer", "Oct 10, 2020"])

  wb = Workbook()
  ws = wb.active

  with open('mock_data.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    for row in csv_reader:
      ws.append(row)
  wb.save('mock_data_xl.xlsx')

if __name__ == "__main__":
  n = int(input("Mock data size: "))
  main(n)