class Discipline:
    def __init__(self, dis_id,  dis_name):
        self.__dis_id = dis_id
        self.__dis_name = dis_name

    @property
    def dis_id(self):
        return self.__dis_id

    @dis_id.setter
    def dis_id(self, new_value):
        self.__dis_id = new_value

    @property
    def dis_name(self):
        return self.__dis_name

    @dis_name.setter
    def dis_name(self, new_value):
        self.__dis_name = new_value

    def __str__(self):
        return "ID: " + str(self.dis_id) + '. ' + "Discipline: " + str(self.dis_name)
