import pickle
import unittest
from src.domain.grade import Grade
from src.errors.error import RepositoryErrors
from src.repository.collection import Collection


class GradeRepository:
    def __init__(self):
        self.__grade_list = Collection()

    def __len__(self):
        return len(self.__grade_list)

    def add_grade(self, grade_ob):
        """
        :param grade_ob: the object that contains sud id, discipline id and the garde
        :return: None
        """
        self.__grade_list.append(grade_ob)

    def add_multiple_grades(self, grades):
        for grade in grades:
            self.add_grade(grade)

    def remove_grade(self, obj):
        self.__grade_list.remove(obj)

    def remove_student_grades(self, stud_id, delete_list):
        """
        :param stud_id: The id of the student that is removed from the student list
        :param delete_list: A list containing the student who has been deleted
        :return: The list containing the object that we removed
        """
        copy_list = self.__grade_list.copy()
        for grade in copy_list:
            if int(grade.stud_id) == int(stud_id):
                delete_list.append(grade)
                self.__grade_list.remove(grade)

    def remove_discipline(self, disc_id, delete_list):
        """
        :param disc_id: The id of the discipline that is removed from the discipline list
        :param delete_list: A list containing the students who have been deleted
        :return: None
        """
        copy_list = self.__grade_list.copy()
        for grade in copy_list:
            if int(grade.disc_id) == int(disc_id):
                delete_list.append(grade)
                self.__grade_list.remove(grade)

    def grade_list(self):
        """
        :return: The list of grades
        """
        # if len(self.__grade_list) > 0:
        return self.__grade_list
        # raise Exception("None of the students has been graded yet!")

    def __getitem__(self, item):
        return self.__grade_list[item]

    # WEEK 9
    def student_average_score_discipline(self, stud_id, disc_id):
        """
        We calculate a student's average score at a certain discipline
        :param stud_id: Student's name
        :param disc_id: Discipline's name
        :return: the average score if the student has at least one grade, -1 otherwise
        """
        grade_sum = 0
        count = 0
        for grade in self.__grade_list:
            if str(stud_id) == str(grade.stud_id) and str(disc_id) == str(grade.disc_id):
                grade_sum += float(grade.grade_value)
                count += 1

        if count == 0:
            return -1

        average = float(grade_sum/count)
        return average

    def set_list(self, grade_list):
        self.__grade_list = grade_list


class GradeFileTextRepository(GradeRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "grade.txt"
        self.load_file()

    def load_file(self):
        with open(self.text_file, "r") as f:
            for line in f.readlines():
                line = line.strip()
                line = line.replace("\n", "")
                if len(line) > 1:
                    stud_id, disc_id, grade = line.split(",", maxsplit=2)
                    obj = Grade(stud_id, disc_id, grade)
                    self.add_grade(obj)

    def save_file(self):
        with open(self.text_file, "w") as f:
            grade_list = self.grade_list()
            for grade in grade_list:
                f.write(str(grade.stud_id) + "," + str(grade.disc_id) + "," + str(grade.grade_value) + "\n")

    def add_grade(self, grade_ob):
        super(GradeFileTextRepository, self).add_grade(grade_ob)
        self.save_file()

    def add_multiple_grades(self, grades):
        super(GradeFileTextRepository, self).add_multiple_grades(grades)
        self.save_file()

    def remove_grade(self, obj):
        super(GradeFileTextRepository, self).remove_grade(obj)
        self.save_file()

    def remove_student_grades(self, stud_id, delete_list):
        super(GradeFileTextRepository, self).remove_student_grades(stud_id, delete_list)
        self.save_file()

    def remove_discipline(self, disc_id, delete_list):
        super(GradeFileTextRepository, self).remove_discipline(disc_id, delete_list)
        self.save_file()


class GradeBinaryFileRepository(GradeRepository):
    def __init__(self):
        super().__init__()
        self.text_file = "grade.bin"
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
            self.data = self.grade_list()
            pickle.dump(self.data, f)

    def add_grade(self, grade_ob):
        super(GradeBinaryFileRepository, self).add_grade(grade_ob)
        self.save_file()

    def add_multiple_grades(self, grades):
        super(GradeBinaryFileRepository, self).add_multiple_grades(grades)
        self.save_file()

    def remove_grade(self, obj):
        super(GradeBinaryFileRepository, self).remove_grade(obj)
        self.save_file()

    def remove_student_grades(self, stud_id, delete_list):
        super(GradeBinaryFileRepository, self).remove_student_grades(stud_id, delete_list)
        self.save_file()

    def remove_discipline(self, disc_id, delete_list):
        super(GradeBinaryFileRepository, self).remove_discipline(disc_id, delete_list)
        self.save_file()


class GradeRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = GradeRepository()
        self._grade = Grade('0', '0', '10')
        self._repo.add_grade(self._grade)

    '''
    Define test functions (test cases) using functions named test_*
    '''

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 1)
        self._repo.add_grade(Grade('1', '1', '9'))
        self._repo.add_grade(Grade('2', '2', '4'))
        self._repo.add_grade(Grade('1', '1', '5'))
        self.assertEqual(len(self._repo), 4)

        # test average_grade_discipline
        result = self._repo.average_grade_discipline('1')
        self.assertEqual(result, 7.0)

        # test student_average_score_discipline
        result = self._repo.student_average_score_discipline('1', '1')
        self.assertEqual(result, 7.0)

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment (clause files, DB connections, deallocate memory)

        :return: None
        """
        self._repo = None
