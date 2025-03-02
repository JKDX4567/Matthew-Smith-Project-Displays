#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Dec  5 18:16:14 2023

@author: mattsmith
"""


def calculate_price(numStudents, length, familyDiscount):
    price = 0
    numStu = int(numStudents[0])
    length_actual = int(length[0:2])
    if numStu == 2:
        price = 23*2
    elif numStu == 3:
        price = 18*3
    elif numStu == 1:
        if length_actual == 30:
            price = 23
        elif length_actual == 45:
            price = 32
        elif length_actual== 60:
            price = 41.5
    if familyDiscount == "Family discount":
        price = price * 0.9
    return price
    
 
student_info = {}
#key = name, value = address
appointment_info = []
#List of lists [0] = name, [1] = date, [2] = time, [3] = numPeople, [4] = length, [5] = location, [6] = Family Discount, [7] = price,  

student_data = open ("Student_info.txt", 'r+')
while True:
    line = student_data.readline()
    if line == "":
        break
    space_index = line.index(" ")
    student_info[line[0:space_index]] = line[space_index+1:]

appointment_data = open ("Appointment_info.txt", 'r+')
counter = 0
while True:
    line = appointment_data.readline()
    if line == "":
        break
    line_true = eval(line)
    if counter == 0:
        appointment_info.append(line_true)
    else:
        appointment_info.append(line_true)
    counter = counter + 1
#This looks stupid but for somereason this fixes a bug soooooooo it stays

still_going = 0
while still_going == 0:
#Keeps running until she wants it to stop
        print("What would you like to do? ")
        print("Options (Just put the number);")
        print("1:Look up appointments")
        print("2:Make an appointment")
        print("3:Cancel an appointment/ Remove an appointmnet")
        print("4:Adjust appoitment")
        print("5:Look up student info (n's mean transfered from data base')")
        print("6:Add student")
        print("7:Remove student")
        print("8:Adjust student info")
        print("9:Total billing")
        print("10:End for now")
        user_input = input()
        #Figures out what she wants to do  
        while user_input != "1" and user_input != "2" and user_input != "3" and user_input != "4" and user_input != "5" and user_input != "6" and user_input != "7" and user_input != "8" and user_input != "9" and user_input != "10":
            user_input = input("Invalid input. Try again ")
        if user_input == "10":
            print("Saving to files. Goodluck ")
            appointment_data.seek(0)
            for x in appointment_info:
                appointment_data.write(str(x) + "\n")
            appointment_data.truncate()
            student_data.seek(0)
            for x in student_info:
                student_data.write(x + " " + student_info[x].strip() + "\n")
            student_data.truncate()
            still_going = 1
        #Ends the program and saves the information    
        
        elif user_input == "1":
            print("Showing appointments now: ")
            for i in appointment_info:
                print(i)
            print(" ")
         #Shows all appointments listed   
        
        elif user_input == "2":
            if len(student_info) == 0:
                print ("No students. Add a student before making any appointments")
                print(" ")
            else:
                counter = 0
                counter2 = 0
                new_appointment1 = input("Student? ")
                for x in student_info:
                    if x == new_appointment1:
                        print("Student found")
                        counter2 = 1
                    else:
                        counter = counter + 1
                while counter2 == 0:
                    counter = 0
                    new_appointment1 = input("No sutdent found. Student? ")
                    for x in student_info:
                        if x == new_appointment1:
                            print("Student found")
                            counter2 = 1
                        else:
                            counter = counter + 1
                new_appointment2 = input("Date (Ex: 01-30-23)? ")
                new_appointment3 = input("Time (Ex: 8:30 PM)? ")
                new_appointment4 = input("Number of people (1, 2, 3)? ")
                while new_appointment4 != "1" and new_appointment4 != "2" and new_appointment4 != "3":
                    new_appointment4 = input("Invlaid number. Number of people (1, 2, 3)? ")
                if new_appointment4 == "2" or new_appointment4 == "3":
                    new_appointment4 = new_appointment4 + " People"
                    new_appointment5 = "60 min"
                else:
                    new_appointment4 = new_appointment4 + " Person"
                    new_appointment5 = input("Length (30,45,60)? ")
                    while new_appointment5 != "30" and new_appointment5 != "45" and new_appointment5 != "60":
                        new_appointment5 = input("Invalid Length. Length (30,45,60)? ")
                    new_appointment5 = new_appointment5 + " Min"
                new_appointment6 = input("Location(Student/Local)? ")
                while new_appointment6 != "Local" and new_appointment6 != "Student":
                    new_appointment6 = input("Invalid location. Location(Student/Local)? ")
                if new_appointment6 == "Student":
                    new_appointment6 = student_info[new_appointment1]     
                new_appointment7 = input("Family discount (Y/N)? ")
                if new_appointment7 == "Y":
                    new_appointment7 = "Family discount"
                elif new_appointment7 == "N":
                    new_appointment7 = "No family discount"
                else:
                    while new_appointment7 != "N" and new_appointment7 != "Y":
                        new_appointment7 = input("Invalid input. Family discount (Y/N)? ")
                    if new_appointment7 == "Y":
                        new_appointment7 = "Family discount"
                    elif new_appointment7 == "N":
                        new_appointment7 = "No family discount"
                price = calculate_price(new_appointment4, new_appointment5, new_appointment7)
                appointment_info.append([new_appointment1, new_appointment2, new_appointment3, new_appointment4, new_appointment5, new_appointment6, new_appointment7, price])
                print(appointment_info)
                print(" ")
        #Adds an appointment to the list of appointments
    
        elif user_input == "3":
            counter = 0
            counter2 = 0
            appointment_cancel1 = input("Student? ")
            appointment_cancel2 = input("Date? (MM-DD-YY) ")
            appointment_cancel3 = input("Time (Include AM or PM with a space. Ex: 8:30 PM)? ")
            for x in appointment_info:
                if x[0] == appointment_cancel1:
                    if x[1] == appointment_cancel2:
                        if x[2] == appointment_cancel3:
                            del appointment_info[counter]
                            counter2 = 1
                        else:
                           counter = counter + 1                            
                    else:
                        counter = counter + 1
                else:
                    counter = counter + 1
            if counter2 == 0:
                print("No matches")
                print(" ")
            elif counter2 == 1:
                print("Match found and removed")
                print(" ")
        #Removes an appoint given the name, time, and date (no appointment will share all three)
        
        elif user_input == "4":
            if len(student_info) == 0:
                print ("No students. Add a student before adjusting any appointments")
                print(" ")
            else:
                counter = 0
                counter2 = 0
                appointment_cancel1 = input("Student? ")
                appointment_cancel2 = input("Date? (MM-DD-YY) ")
                appointment_cancel3 = input("Time (Include AM or PM with a space. Ex: 8:30 PM)? ")
                for x in appointment_info:
                    if x[0] == appointment_cancel1:
                        if x[1] == appointment_cancel2:
                            if x[2] == appointment_cancel3:
                                print("Found a match, update the appointment ")
                                counter5 = 0
                                for y in student_info:
                                    if y == appointment_cancel1:
                                        counter5 = 1
                                    if counter5 == 0:
                                        r_u_sure = input("The student no longer exists in this data base. You wont be able to adjust this appointment with the same student. Are you sure you want to continue (Y/N)")
                                        while r_u_sure != "N" and r_u_sure != "Y":
                                            r_u_sure = input("Invalid input. Are you sure you want to adjust(Y/N)? ")
                                        if r_u_sure == "Y":
                                            counter5 = 1
                                        elif r_u_sure == "N":
                                            counter5 = 0
                                if counter5 == 1:                                    
                                    counter3 = 0
                                    counter4 = 0
                                    new_appointment1 = input("Student? ")
                                    for x in student_info:
                                        if x == new_appointment1:
                                            print("Student found")
                                            counter4 = 1
                                        else:
                                            counter3 = counter3 + 1
                                    while counter4 == 0:
                                        counter3 = 0
                                        new_appointment1 = input("No sutdent found. Student? ")
                                        for x in student_info:
                                            if x == new_appointment1:
                                                print("Student found")
                                                counter4 = 1
                                            else:
                                                counter3 = counter3 + 1
                                    new_appointment2 = input("Date (Ex: MM-DD-YY)? ")
                                    new_appointment3 = input("Time (Ex: 8:30 PM)? ")
                                    new_appointment4 = input("Number of people (1, 2, 3)? ")
                                    while new_appointment4 != "1" and new_appointment4 != "2" and new_appointment4 != "3":
                                        new_appointment4 = input("Invlaid number. Number of people (1, 2, 3)? ")
                                    if new_appointment4 == "2" or new_appointment4 == "3":
                                        new_appointment4 = new_appointment4 + " People"
                                        new_appointment5 = "60 min"
                                    else:
                                        new_appointment4 = new_appointment4 + " Person"
                                        new_appointment5 = input("Length (30,45,60)? ")
                                        while new_appointment5 != "30" and new_appointment5 != "45" and new_appointment5 != "60":
                                            new_appointment5 = input("Invalid Length. Length (30,45,60)? ")
                                        new_appointment5 = new_appointment5 + " Min"
                                    new_appointment6 = input("Location(Student/Local)? ")
                                    while new_appointment6 != "Local" and new_appointment6 != "Student":
                                        new_appointment6 = input("Invalid location. Location(Student/Local)? ")
                                    if new_appointment6 == "Student":
                                        new_appointment6 = student_info[new_appointment1]        
                                    new_appointment7 = input("Family discount (Y/N)? ")
                                    if new_appointment7 == "Y":
                                        new_appointment7 = "Family discount"
                                    elif new_appointment7 == "N":
                                        new_appointment7 = "No family discount"
                                    else:
                                        while new_appointment7 != "N" and new_appointment7 != "Y":
                                            new_appointment7 = input("Invalid input. Family discount (Y/N)? ")
                                        if new_appointment7 == "Y":
                                            new_appointment7 = "Family discount"
                                        elif new_appointment7 == "N":
                                            new_appointment7 = "No family discount"
                                    price = calculate_price(new_appointment4, new_appointment5, new_appointment7)
                                    appointment_info[counter] = [new_appointment1, new_appointment2, new_appointment3, new_appointment4, new_appointment5, new_appointment6, new_appointment7, price]
                                    counter2 = 1
                                elif counter5 == 0:
                                    print("Canceled adjustment")
                            else:
                               counter = counter + 1                            
                        else:
                            counter = counter + 1
                    else:
                        counter = counter + 1
                if counter2 == 0:
                    print("No matches")
                    print(" ")
                elif counter2 == 1:
                    print("Match found and updated")
                    print(" ")
        #Replaces an appointment with different information 
        
        elif user_input == "5":
            print("Student information: ")
            print(student_info)
            print(" ")
        #Shows all the student information
        
        elif user_input == "6":
            new_student1 = input("New student Name (Just first name, use just last name only if first name is shared with somone)? ")
            new_student2 = input("Student address: ")
            student_info[new_student1] =  new_student2
            print(" ")
        #Adds a new student and their adress
        
        elif user_input == "7":
            counter = 0
            counter2 = 0
            remove_student = input("Student Name? ")
            for y in appointment_info:
                if y[0] == remove_student:
                    print("This student is in an appointment. Remvoing them will make their appointments un-adjustable. Are you sure you wish to continue? (Y/N) ")
                    r_u_sure = input()
                    while r_u_sure != "N" and r_u_sure != "Y":
                        r_u_sure = input("Invalid input. Are you sure you want to remove(Y/N)? ")
                    if r_u_sure == "Y":
                        for x in student_info:
                            if x == remove_student:
                                print("Match found, removing person now ")
                                student_info.pop(x)
                                counter2 = 1
                                counter = 1
                                break
                        if counter2 == 1:
                            print("Student removed")
                            print(" ")
                        elif counter2 ==0:
                            print(" ")
                    elif r_u_sure == "N":
                        print("Canceled removal")
                        print(" ")
                        counter = 2
            if counter == 0:
                for x in student_info:
                    if x == remove_student:
                        print("Match found, removing person now ")
                        student_info.pop(x)
                        counter2 = 1
                        counter = 1
                        break
                if counter2 == 1:
                    print("Student removed")
                    print(" ")
                elif counter2 ==0:
                    print("No match found")
                    print(" ")
        #Removes a student from the list
        
        elif user_input == "8":
            counter2 = 0
            adjust_student = input("Student Name? ")
            for x in student_info:
                if x == adjust_student:
                    print("Match found, adjust the address now ")
                    adjust_adress = input("New adress? ")
                    student_info[adjust_student] = adjust_adress
                    counter2 = 1
                    for y in appointment_info:
                        if y[0] == adjust_student:
                            y[5] = adjust_adress
                            print("Adjusted an appointment")
                    break
            if counter2 == 1:
                print("Student info adjusted")
                print(" ")
            elif counter2 ==0:
                print("No match found")
                print(" ")
        #Adjusts a student from the list
        
        elif user_input == "9":
            if len(appointment_info) == 0:
                print("No current appointments, so $0")
                print(" ")
            else:                
                billing  = 0
                for x in appointment_info:
                    billing = billing + x[7]
                print("Total billing: $" + str(billing))
                print(" ")
        #Adds up all the money from all the appointments and gives a total
        