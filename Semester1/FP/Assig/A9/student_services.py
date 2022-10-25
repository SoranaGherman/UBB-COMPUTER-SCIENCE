import unittest

from src.errors.error import RepositoryErrors
from src.repository.stud_repo import StudentRepository, StudentFileTextRepository, StudentBinaryFileRepository
from src.domain.student import Student
from src.validations.val import ValidationStudents
from src.services.undo_service import Operation, FunctionCall, CascadedOperation, UndoCtrl


class StudentCtrl:
    def __init__(self, undo_service, repo_type):
        self.__stud_repo = repo_type
        self.__valid_stud = ValidationStudents()
        self.__undo_service = undo_service

    def add_student_undo(self, stud_id, name, pos):
        """
        :param stud_id: student' s id
        :param name: student' s name
        :param pos: student' s position
        :return: None
        """
        self.__stud_repo.add_student_undo(stud_id, name, pos)

    def add_student(self, name):
        """
        :param name: the name of the participant we want to add
        :return: None
        """
        try:
            self.__valid_stud.validation_stud(name)
            self.__stud_repo.add_student(name)
            student_list = self.student_list()
            student = student_list[-1]
            fc_undo = FunctionCall(self.remove_student, student.stud_id)
            fc_redo = FunctionCall(self.add_student_undo, student.stud_id, student.stud_name,
                                   int(len(self.__stud_repo.student_list())-1))
            cope = CascadedOperation()
            cope.add(Operation(fc_undo, fc_redo))
            self.__undo_service.record_operation(cope)

        except Exception as ex:
            raise ex

    def remove_student(self, stud_id):
        """
        :param stud_id:  The id of the student we want to remove
        :return: None
        """
        try:
            return self.__stud_repo.remove_student(stud_id)
        except RepositoryErrors as er:
            raise er

    def update_student(self, stud_id, updated_name):
        """
        :param stud_id: The id of the student we want to update
        :param updated_name: The name we want to give to this student
        :return: None
        """
        try:
            name = self.__stud_repo.find_stud_by_id(stud_id)
            self.__valid_stud.validation_stud(updated_name)
            self.__stud_repo.update_student(stud_id, updated_name)
            fc_undo = FunctionCall(self.__stud_repo.update_student, stud_id, name)
            fc_redo = FunctionCall(self.__stud_repo.update_student, stud_id, updated_name)
            cope = Operation(fc_undo, fc_redo)
            self.__undo_service.record_operation(cope)
        except RepositoryErrors as re:
            raise re

    def student_list(self):
        """
        :return: The current list
        """
        return self.__stud_repo.student_list()

    def find_stud_by_id(self, id_stud):
        """
        :param id_stud: The student's id
        :return: True if the student exists, None otherwise
        """
        try:
            return self.__stud_repo.find_stud_by_id(id_stud)
        except RepositoryErrors as re:
            raise re

    def __len__(self):
        return len(self.__stud_repo)

    def __getitem__(self, item):
        return self.__stud_repo[item]

    # WEEK 9
    def search_student(self, name):
        """
        Search a student
        :param name: What we are searching for
        :return: A list containing all associations
        """
        try:
            return self.__stud_repo.search_student(name)
        except RepositoryErrors as re:
            raise re

    # WEEK 10
    def undo(self):
        try:
            self.__undo_service.undo()
        except Exception as ex:
            raise ex

    def redo(self):
        try:
            self.__undo_service.redo()
        except Exception as ex:
            raise ex


class StudentServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = StudentCtrl(UndoCtrl())
        self._repo.add_student('Sunny Storm')

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

    def test_validation_exception(self):
        pass

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment (clause files, DB connections, deallocate memory)

        :return: None
        """
        self._repo = None



