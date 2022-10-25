import random
import unittest

import src.repository.collection
from src.domain.grade import Grade
from src.errors.error import RepositoryErrors
from src.repository.grade_repo import GradeRepository
from src.services.undo_service import FunctionCall, CascadedOperation, Operation
from src.validations.val import GradeValidation


class GradeCtrl:
    def __init__(self, student_service, discipline_service, undo_service, repo_type):
        self.__grade_repo = repo_type
        self.__undo_service = undo_service
        self.__student_service = student_service
        self.__discipline_service = discipline_service
        self.__stud_list = self.__student_service.student_list()
        self.__disc_list = self.__discipline_service.discipline_list()
        self.__validation_grades = GradeValidation()
        # self.generate_grades()

    def add_grade(self, stud_id, disc_id, grade):
        """
        :param stud_id: the student we want to grade
        :param disc_id: the discipline we want to grade the student at
        :param grade: the grade we want to give
        :return: None
        """
        try:
            grade_obj = Grade(stud_id, disc_id, grade)
            self.__validation_grades.validation_grade(grade)
            self.__grade_repo.add_grade(grade_obj)
            fc_undo = FunctionCall(self.__grade_repo.remove_grade, grade_obj)
            fc_redo = FunctionCall(self.__grade_repo.add_grade, grade_obj)
            cope = Operation(fc_undo, fc_redo)
            self.__undo_service.record_operation(cope)
        except Exception as ex:
            raise ex

    # def generate_grades(self):
    #     """
    #     We generate 20 random grades to the students already generated
    #     :return: None
    #     """
    #     for i in range(len(self.__stud_list)):
    #         grade_obj = Grade(self.__stud_list[i].stud_id, self.__disc_list[i].dis_id,
    #                           random.randint(1, 10))
    #         self.__grade_repo.add_grade(grade_obj)

    def remove_student_grades(self, stud_id):
        """
        :param stud_id: The id of the student we want to remove
        :return: None
        """
        try:
            deleted_list = list()
            d = list()
            self.__grade_repo.remove_student_grades(stud_id, deleted_list)
            fc_undo = FunctionCall(self.__grade_repo.add_multiple_grades, deleted_list)
            fc_redo = FunctionCall(self.__grade_repo.remove_student_grades, stud_id, d)
            cope = CascadedOperation()
            cope.add(Operation(fc_undo, fc_redo))

            name = self.__student_service.find_stud_by_id(stud_id)
            index = self.__student_service.remove_student(stud_id)
            fc_undo = FunctionCall(self.__student_service.add_student_undo, stud_id, name, index)
            fc_redo = FunctionCall(self.__student_service.remove_student, stud_id)
            cope.add(Operation(fc_undo, fc_redo))
            self.__undo_service.record_operation(cope)

        except Exception as ex:
            raise ex

    def remove_discipline(self, disc_id):
        """
        :param disc_id: The id of the discipline we want to remove
        :return: None
        """
        try:
            deleted_list = list()
            d = list()
            self.__grade_repo.remove_discipline(disc_id, deleted_list)
            fc_undo = FunctionCall(self.__grade_repo.add_multiple_grades, deleted_list)
            fc_redo = FunctionCall(self.__grade_repo.remove_discipline, disc_id, d)
            cope = CascadedOperation()
            cope.add(Operation(fc_undo, fc_redo))

            name = self.__discipline_service.find_disc_by_id(disc_id)
            index = self.__discipline_service.remove_discipline(disc_id)
            fc_undo = FunctionCall(self.__discipline_service.add_discipline_undo, disc_id, name, index)
            fc_redo = FunctionCall(self.__discipline_service.remove_discipline, disc_id)
            cope.add(Operation(fc_undo, fc_redo))
            self.__undo_service.record_operation(cope)

        except Exception as ex:
            raise ex

    def grade_list(self):
        """
        :return: The list of grades
        """
        # try:
        return self.__grade_repo.grade_list()
        # except Exception as er:
        #     raise er

    def __len__(self):
        return len(self.__grade_repo)

    def __getitem__(self, item):
        return self.__grade_repo[item]

    # WEEK 9
    def average_grade_discipline(self):
        """"
        :return: The list containing all disciplines where there exist at least one grade
        """
        dis_list = list()
        all_disciplines_list = list()
        found = False

        for disc in self.__disc_list:
            all_disciplines_list.append([disc.dis_id, disc.dis_name])

        def pass_function(element):
            grade_list = self.__grade_repo.grade_list()
            for grade in grade_list:
                if int(element[0]) == int(grade.disc_id):
                    return True
            return False

        result = src.repository.collection.filter_data(all_disciplines_list, pass_function)
        if len(result) > 0:
            found = True

        for res in result:
            grade_sum = 0
            count = 0
            for stud in self.__stud_list:
                if 0 < self.student_average_score_discipline(stud.stud_id, res[0]):
                    grade_sum += self.student_average_score_discipline(stud.stud_id, res[0])
                    count += 1

            if count:
                aggregated_average = float(grade_sum / count)
                if aggregated_average > 0:
                    dis_list.append([res[0], res[1], aggregated_average])

        if found:
            return dis_list
        raise RepositoryErrors("None of the disciplines has been graded yet!")

    @staticmethod
    def comparison_greater_function_by_third_element(elem1, elem2):
        if elem1[2] > elem2[2]:
            return True
        return False

    @staticmethod
    def comparison_greater_function_by_first_element(elem1, elem2):
        if elem1[0] > elem2[0]:
            return True
        return False

    @staticmethod
    def comparison_greater_function_by_second_element(elem1, elem2):
        if elem1[1] > elem2[1]:
            return True
        return False

    def sort_by_average_discipline(self, data):
        result = src.repository.collection.comb_sort(data, self.comparison_greater_function_by_third_element)
        return result

    def sort_by_index(self, data):
        result = src.repository.collection.comb_sort(data, self.comparison_greater_function_by_first_element)
        return result

    def sort_by_average_situation(self, data):
        result = src.repository.collection.comb_sort(data, self.comparison_greater_function_by_second_element)
        return result

    def student_average_score_discipline(self, stud_id, disc_id):
        """
        We calculate a student's average score at a certain discipline
        :param stud_id: Student's name
        :param disc_id: Discipline's name
        :return: True if it is >=5 , False otherwise
        """
        return self.__grade_repo.student_average_score_discipline(stud_id, disc_id)

    def student_fails_discipline(self):
        """
        :return: The list of students who failed at least one discipline
        """
        my_list = list()
        found = False

        def pass_function(element):
            if 0 < self.student_average_score_discipline(element[0], element[2]) < 5:
                return True
            return False

        for stud in self.__stud_list:
            for disc in self.__disc_list:
                my_list.append([stud.stud_id, stud.stud_name, disc.dis_id])

        result = src.repository.collection.filter_data(my_list, pass_function)
        if len(result) > 0:
            found = True

        unique_result = list()
        ok = 1
        for i in range(len(result)):
            for j in range(len(unique_result)):
                if result[i][0] == unique_result[j][0] and ok:
                    ok = 0

            if ok:
                unique_result.append(result[i])

        if found:
            return unique_result
        raise RepositoryErrors("Nobody failed!")

    def students_situation(self):
        """
        :return: A list containing all students' aggregated average
        """
        display_list = list()
        found = False
        for stud in self.__stud_list:
            grade_sum = 0
            count = 0
            for disc in self.__disc_list:
                if 0 < self.student_average_score_discipline(stud.stud_id, disc.dis_id):
                    grade_sum += self.student_average_score_discipline(stud.stud_id, disc.dis_id)
                    count += 1
                    found = True

            if count:
                aggregated_average = float(grade_sum / count)
                if aggregated_average > 0:
                    display_list.append([stud.stud_id, aggregated_average, stud.stud_name])

        if found:
            return display_list
        raise RepositoryErrors("Nobody has been graded yet!")


class GradeServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = GradeCtrl([], [])
        self._repo.add_grade('0', '0', '10')

    '''
    Define test functions (test cases) using functions named test_*
    '''

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 1)
        self._repo.add_grade('1', '1', '9')
        self._repo.add_grade('2', '2', '4')
        self._repo.add_grade('3', '3', '6')
        self.assertEqual(len(self._repo), 4)

        # test average_grade_discipline
        pass

    def test_validation_exception(self):
        pass

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment (clause files, DB connections, deallocate memory)

        :return: None
        """
        self._repo = None

