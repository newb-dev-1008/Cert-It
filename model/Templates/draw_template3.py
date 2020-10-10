from PIL import Image, ImageDraw, ImageFont
import pandas as pd


import zipfile
import os


from datetime import datetime
import random
import string

df = pd.read_csv('data.csv')

new_columns = ['Name', 'Company_Name', 'Certificate','Certifier1' , 'Certifier_Position1',
	    	'Certifier2', 'Certifier_Position2', 'Date']

df.columns = new_columns

df = df.applymap(str)

def paste(cordinates ,text_color, font , font_size ,string):
	
	f_font = ImageFont.truetype(font,font_size)

	d.text(cordinates, string , align='center',fill =text_color, font = f_font)


def paste_bold(cordinates ,text_color, font , font_size ,string):
	
	f_font = ImageFont.truetype(font,font_size)

	new_x = cordinates[0] + 1
	new_y = cordinates[1] + 1
	new_xx = cordinates[0] - 1
	new_yy = cordinates[1] -1

	up = (new_x , new_y)
	down = (new_xx , new_yy)

	d.text(cordinates, string , fill =text_color, font = f_font)
	d.text(up, string , fill =text_color, font = f_font)
	d.text(down, string , fill =text_color, font = f_font)



def center_align(name):

	f_font = ImageFont.truetype(oswald,110)
	bounding_box = [440,690 , 1990 , 810]
	x1, y1, x2, y2 = bounding_box  

	w, h = d.textsize(name, font=f_font)

	x = (x2 - x1 - w)/2 + x1
	y = (y2 - y1 - h)/2 + y1

	d.text((x, y), name,  fill =(102,205,170),align='center', font=f_font)
	d.text((x+2, y+2),name ,  fill =(102,205,170),align='center', font=f_font)
	d.text((x-2, y-2), name,  fill =(102,205,170),align='center', font=f_font)


def create_data(df):

	for index, rows in df.iterrows(): 

	    my_list = [rows.Name, rows.Company_Name, rows.Certificate,rows.Certifier1 , rows.Certifier_Position1,
	    			rows.Certifier2, rows.Certifier_Position2, rows.Date] 
	       
	    rows_list.append(my_list)


def convert_date(string):

	date_time_obj = datetime.strptime(string, '%d/%m/%Y')

	month_format = date_time_obj.strftime("%e/%B/%Y")

	month_format = str(month_format)

	new_format = month_format.replace("/", " ")
	string_list = new_format.split()

	if string_list[0] == '1' or string_list[0] == '11' or string_list[0] == '21' or string_list[0] == '31':

		string_list[0] += 'st'

	elif string_list[0] == '2' or string_list[0] == '12' or string_list[0] == '22':

		string_list[0] += 'nd'

	elif string_list[0] == '3' or string_list[0] == '13' or string_list[0] == '23':

		string_list[0] += 'rd'
	else:
		string_list[0] = string_list[0] + 'th'

	string = ' '.join(string_list)

	return string


def unique_id(list1):

	x = '#' + ''.join(random.choices(string.ascii_letters + string.digits, k=16))

	if len(list1) == 0:

		return str(x)

	if len(list1) != 0:

		if x in list1:

			y = '#' + ''.join(random.choices(string.ascii_letters + string.digits, k=16))

			return str(y)

		else:

			return str(x)




georgia = 'georgia.ttf'
arial = 'arial.ttf'
oswald = r'C:\Users\ROSHAN\Certificate Maker\Fonts\Oswald-Bold.ttf'
oswald_light = r'C:\Users\ROSHAN\Certificate Maker\Fonts\Oswald-Light.ttf'

rows_list = [] 
unique_id_list = []

create_data(df)

length = len(df)

for i in range(0,length):

	unique_id1 = unique_id(unique_id_list)

	unique_id_list.append(unique_id1)

for val in range(0,len(rows_list)):

   rows_list[val].insert(8,unique_id_list[val])


for rows in rows_list:

	path = r'C:\Users\ROSHAN\Certificate Maker\Images'  #Your directory

	filelist= [file for file in os.listdir(path) if file.endswith('.png') or file.endswith('jpg') or file.endswith('jpeg')]
	
	for images in filelist:

			im = Image.open(images) #Ensure only your one image is in the directory	

	#im = Image.open("Template3_TBD.png")

	d = ImageDraw.Draw(im)
	
	paste_bold((975,150) , 	(220,20,60), oswald, 110 ,rows[1])	

	paste_bold((650,300) , (0,0,128), oswald, 110 ,rows[2])

	paste((975,500) , (0,0,128), oswald_light, 80 , 'is presented to')

	if len(rows[0]) > 20:

		new_name = rows[0].replace(" ", "\n")
		center_align(new_name)
	
	else:
		center_align(rows[0])

	paste((975,900) , (70,130,180),oswald_light, 50 , 'On '  + convert_date(rows[7]))

	paste((670,1000) , 	(0,0,128),oswald_light , 60 , 'For potraying examplary skills and completing')
	paste((670,1065) , 	(0,0,128),oswald_light , 60 , 'all assigned tasks')

	paste_bold((240 , 900) , (102,205,170), georgia ,40 , rows[3])
	paste((240 , 950), (0,0,128) , oswald_light , 30 , rows[4])

	paste_bold((240,1135), (102,205,170) ,georgia , 40 , rows[5])
	paste((240 , 1180), (0,0,128) , oswald_light , 30 , rows[6])

	paste((975 , 1270), (0,0,128) , oswald_light , 35 , rows[8])	

	im.save('Certificate_' + rows[0] + '.png')

