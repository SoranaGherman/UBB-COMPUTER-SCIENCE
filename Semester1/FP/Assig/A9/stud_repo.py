import unittest
import names
import pickle
from random import randint

import src.repository.collection
from src.domain.student import Student
from src.errors.error import RepositoryErrors
from src.repository.collection import Collection


class StudentRepository:
    def __init__(self):
        self.__start_id = 1
        self.__end_id = 10
        self.__student_list = Collection()

        # for i in range(20):
        #     self.__student_list.append(Student(randint(self.__start_id, self.__end_id), names.get_full_name()))
        #     self.__start_id = self.__end_id + 1
        #     self.__end_id += 20

    def add_student(self, name):
        """
        :param name: the name of the student we want to add to repo
        :return: None
        """
        ok = False
        while not ok:
            rand_id = randint(self.__start_id, self.__end_id)
            obj = Student(rand_id, name)
            ok = True
            for student in self.__student_list:
                if str(student.stud_id) == str(rand_id):
                    ok = False

        self.__student_list.append(obj)
        self.__start_id = self.__end_id + 10
        self.__end_id += 20

        return obj

    def add_student_undo(self, stud_id, name, pos):
        obj = Student(stud_id, name)
        self.__student_list.insert(pos, obj)

    def add_student_by_id(self, stud_id, name):
        obj = Student(stud_id, name)
        self.__student_list.append(obj)

    def remove_student(self, student_id):
        """
        :param student_id: The id of the student we want to remove from repo
        :return: position
        """
        index = 0
        for stud in self.__student_list:
            current_id = int(stud.stud_id)
            if current_id == int(student_id):
                self.__student_list.remove(stud)
                return index
            index += 1

    def update_student(self, student_id, updated_name):
        """
        :param student_id: The id of the student we want to update
        :param updated_name: The name we want to give to this student
        :return: None
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].stud_id) == int(student_id):
                self.__student_list[i].stud_name = updated_name

    def student_list(self):
        """
        :return: The current list
        """
        return self.__student_list

    def find_stud_by_id(self, id_stud):
        """
        :param id_stud: The student's id
        :return: True if the student exists, None otherwise
        """
        for i in range(len(self.__student_list)):
            if int(self.__student_list[i].stud_id) == int(id_stud):
                return self.__student_list[i].stud_name
        raise RepositoryErrors("The student from this id does not exist!")

    def __len__(self):
        return len(self.__student_list)

    def __getitem__(self, item):
        return self.__student_list[item]

    def set_list(self, student_list):
        self.__student_list = student_list

    # WEEK 9
    def search_student(self, name):
        """
        Search for a student
        :param name: What we are searching for
        :return: A list containing all associations
        """
        # search_list = list()
        # found = False
        # if name == "":
        #     raise RepositoryErrors("Nothing to match!")
        #
        # for stud in self.__student_list:
        #     if name.lower() in stud.stud_name.lower():
        #         search_list.append([stud.stud_id, stud.stud_name])
        #         found = True
        #     elif str(name) in str(stud.stud_id):
        #         search_list.append([stud.stud_id, stud.stud_name])
        #         found = True

        def pass_function(element):
            if str(name).lower() in str(element.stud_name).lower():
                return True
            elif str(name).lower() in str(element.stud_id).lower():
                return True
            return False

        result = src.repository.collection.filter_data(self.__student_list, pass_function)
        if len(result) > 0:
            return result
        raise RepositoryErrors("Nothing to match!")


class StudentFileTextRepository(StudentRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "students.txt"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "r") as f:
            for line in f.readlines():
                line = line.strip()
                line = line.replace("\n", "")
                if len(line) > 1:
                    stud_id, stud_name = line.split(",", maxsplit=1)
                    self.add_student_by_id(stud_id, stud_name)

    def save_file(self):
        with open(self.text_file, "w") as f:
            student_list = self.student_list()
            for stud in student_list:
                f.write(str(stud.stud_id) + "," + str(stud.stud_name) + "\n")

    def add_student(self, name):
        super(StudentFileTextRepository, self).add_student(name)
        self.save_file()

    def add_student_undo(self, stud_id, name, pos):
        super(StudentFileTextRepository, self).add_student_undo(stud_id, name, pos)
        self.save_file()

    def add_student_by_id(self, stud_id, name):
        super(StudentFileTextRepository, self).add_student_by_id(stud_id, name)
        self.save_file()

    def remove_student(self, student_id):
        aux = super(StudentFileTextRepository, self).remove_student(student_id)
        self.save_file()
        return aux

    def update_student(self, student_id, updated_name):
        super(StudentFileTextRepository, self).update_student(student_id, updated_name)
        self.save_file()


class StudentBinaryFileRepository(StudentRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "students.bin"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "rb") as f:
            try:
                self.data = pickle.load(f)
                self.set_list(self.data)
            except EOFError:
                self.data = list()

    def save_file(self):
        with open(self.text_file, "wb") as f:
            self.data = self.student_list()
            pickle.dump(self.data, f)

    def add_student(self, name):
        super(StudentBinaryFileRepository, self).add_student(name)
        self.save_file()

    def add_student_undo(self, stud_id, name, pos):
        super(StudentBinaryFileRepository, self).add_student_undo(stud_id, name, pos)
        self.save_file()

    def add_student_by_id(self, stud_id, name):
        super(StudentBinaryFileRepository, self).add_student_by_id(stud_id, name)
        self.save_file()

    def remove_student(self, student_id):
        aux = super(StudentBinaryFileRepository, self).remove_student(student_id)
        self.save_file()
        return aux

    def update_student(self, student_id, updated_name):
        super(StudentBinaryFileRepository, self).update_student(student_id, updated_name)
        self.save_file()


class StudentRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = StudentRepository()
        self._student = Student('100', 'Marry Lee')
        self._repo.add_student(self._student)

    '''
    Define test functions (test cases) using functions named test_*
    '''

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_student(Student('1', 'Michael Jonson'))
        self._repo.add_student(Student('2', 'Max Jen'))
        self._repo.add_student(Student('0', 'Maria Monica'))
        self.assertEqual(len(self._repo), 24)

        # test remove
        last_student = self._repo[len(self._repo)-1]
        id_last_student = last_student.stud_id
        self._repo.remove_student(id_last_student)
        self.assertEqual(len(self._repo), 23)

        # test update
        last_student = self._repo[len(self._repo) - 1]
        id_last_student = last_student.stud_id
        self._repo.update_student(id_last_student, "Max Jennifer")
        last_student = self._repo[len(self._repo) - 1]
        name_last_student = last_student.stud_name
        self.assertEqual(name_last_student, "Max Jennifer")

        # test student list
        student_list = self._repo.student_list()
        self.assertEqual(student_list[len(student_list)-1].stud_id, id_last_student)

        # test find student name by id
        name_last_student = self._repo.find_stud_by_id(id_last_student)
        self.assertEqual(name_last_student, "Max Jennifer")

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment (clause files, DB connections, deallocate memory)

        :return: None
        """
        self._repo = None
