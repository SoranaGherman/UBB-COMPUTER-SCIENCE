import unittest

from src.errors.error import RepositoryErrors
from src.repository.disc_repo import DisciplineRepo
from src.services.undo_service import FunctionCall, CascadedOperation, Operation
from src.validations.val import ValidationDisciplines


class DisciplineCtrl:
    def __init__(self, undo_service, repo_type):
        self.__disc_repo = repo_type
        self.__undo_service = undo_service
        self.__valid_disc = ValidationDisciplines()

    def add_discipline_undo(self, disc_id, name, pos):
        self.__disc_repo.add_discipline_undo(disc_id, name, pos)

    def add_discipline(self, name):
        """
        :param name: the name of the discipline we want to add
        :return: None
        """
        try:
            self.__valid_disc.validation_disc(name)
            self.__disc_repo.add_discipline(name)
            discipline_list = self.discipline_list()
            discipline = discipline_list[-1]
            fc_undo = FunctionCall(self.remove_discipline, discipline.dis_id)
            fc_redo = FunctionCall(self.add_discipline_undo, discipline.dis_id, discipline.dis_name,
                                   int(len(self.__disc_repo.discipline_list())-1))
            cope = CascadedOperation()
            cope.add(Operation(fc_undo, fc_redo))
            self.__undo_service.record_operation(cope)

        except Exception as ex:
            raise ex

    def remove_discipline(self, disc_id):
        """
        :param disc_id:  The id of the discipline we want to remove
        :return: index
        """
        try:
            return self.__disc_repo.remove_discipline(disc_id)
        except RepositoryErrors as er:
            raise er

    def update_discipline(self, disc_id, updated_name):
        """
        :param disc_id: The id of the student we want to update
        :param updated_name: The name we want to give to this student
        :return: None
        """
        try:
            name = self.__disc_repo.find_disc_by_id(disc_id)
            self.__disc_repo.update_discipline(disc_id, updated_name)
            self.__valid_disc.validation_disc(updated_name)
            fc_undo = FunctionCall(self.__disc_repo.update_discipline, disc_id, name)
            fc_redo = FunctionCall(self.__disc_repo.update_discipline, disc_id, updated_name)
            cope = Operation(fc_undo, fc_redo)
            self.__undo_service.record_operation(cope)
        except RepositoryErrors as re:
            raise re

    def discipline_list(self):
        """
        :return: The current list
        """
        return self.__disc_repo.discipline_list()

    def find_disc_by_id(self, id_disc):
        """
        :param id_disc: The discipline's id
        :return: True if the student exists, None otherwise
        """
        try:
            return self.__disc_repo.find_disc_by_id(id_disc)
        except RepositoryErrors as re:
            raise re

    def __len__(self):
        return len(self.__disc_repo)

    def __getitem__(self, item):
        return self.__disc_repo[item]

    # WEEK 9
    def search_discipline(self, name):
        """
        Search a discipline
        :param name: What we are searching for
        :return: A list containing all associations
        """
        try:
            return self.__disc_repo.search_discipline(name)
        except RepositoryErrors as re:
            raise re


class DisciplineServicesTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = DisciplineCtrl()
        self._repo.add_discipline("Swimming")

    '''
    Define test functions (test cases) using functions named test_*
    '''

    def test_repo_elements(self):
        # test add
        self.assertEqual(len(self._repo), 21)
        self._repo.add_discipline('Geography')
        self._repo.add_discipline('Geometry')
        self._repo.add_discipline('Logic')
        self.assertEqual(len(self._repo), 24)

        # test remove
        last_discipline = self._repo[len(self._repo)-1]
        id_last_discipline = last_discipline.dis_id
        self._repo.remove_discipline(id_last_discipline)
        self.assertEqual(len(self._repo), 23)

        # test update
        last_discipline = self._repo[len(self._repo) - 1]
        id_last_discipline = last_discipline.dis_id
        self._repo.update_discipline(id_last_discipline, "Maths")
        last_discipline = self._repo[len(self._repo) - 1]
        name_last_discipline = last_discipline.dis_name
        self.assertEqual(name_last_discipline, "Maths")

        # test discipline list
        discipline_list = self._repo.discipline_list()
        self.assertEqual(discipline_list[21].dis_name, "Geography")
        self.assertEqual(discipline_list[len(discipline_list)-1].dis_id, id_last_discipline)

        # test find discipline by id
        name_last_discipline = self._repo.find_disc_by_id(id_last_discipline)
        self.assertEqual(name_last_discipline, "Maths")

    def tearDown(self) -> None:
        """
        Runs after all the tests have completed
        Used to close the test environment (clause files, DB connections, deallocate memory)

        :return: None
        """
        self._repo = None



