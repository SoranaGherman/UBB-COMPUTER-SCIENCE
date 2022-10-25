class Grade:
    def __init__(self, stud_id,  disc_id, grade_value):
        self.__stud_id = stud_id
        self.__disc_id = disc_id
        self.__grade_value = grade_value

    @property
    def stud_id(self):
        return self.__stud_id

    @stud_id.setter
    def stud_id(self, new_value):
        self.__stud_id = new_value

    @property
    def disc_id(self):
        return self.__disc_id

    @disc_id.setter
    def disc_id(self, new_value):
        self.__disc_id = new_value

    @property
    def grade_value(self):
        return self.__grade_value

    @grade_value.setter
    def grade_value(self, new_value):
        self.__grade_value = new_value

    def __str__(self):
        return str(self.stud_id) + " " + str(self.disc_id) + " " + str(self.grade_value)
