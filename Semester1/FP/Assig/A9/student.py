class Student:
    def __init__(self, stud_id,  stud_name):
        self.__stud_id = stud_id
        self.__stud_name = stud_name

    @property
    def stud_id(self):
        return self.__stud_id

    @stud_id.setter
    def stud_id(self, new_value):
        self.__stud_id = new_value

    @property
    def stud_name(self):
        return self.__stud_name

    @stud_name.setter
    def stud_name(self, new_value):
        self.__stud_name = new_value

    def __str__(self):
        return "ID: " + str(self.stud_id) + '. ' + "Student " + str(self.stud_name)
