import random
import unittest
import pickle
from random import randint
from src.domain.discipline import Discipline
from src.errors.error import RepositoryErrors
from src.repository.collection import Collection
import src.repository.collection


class DisciplineRepo:
    def __init__(self):
        self.__start_id = 1000
        self.__end_id = 1010
        self.__discipline_list = Collection()
        # self.__d_l = ["Mathematics", "Geology", "Geography", "Informatics", "History", "Music", "Arts",
        #               "Sports", "Logic", "Algebra", "Geometry"]
        # for i in range(20):
        #  self.__discipline_list.append(Discipline(randint(self.__start_id, self.__end_id), random.choice(self.__d_l)))
        #  self.__start_id = self.__end_id + 1
        #  self.__end_id += 20

    def add_discipline(self, name):
        """
        :param name: the name of the discipline we want to add
        :return: obj
        """
        ok = False
        while not ok:
            rand_id = randint(self.__start_id, self.__end_id)
            obj = Discipline(rand_id, name)
            ok = True
            for discipline in self.__discipline_list:
                if str(discipline.dis_id) == str(rand_id):
                    ok = False

        self.__discipline_list.append(obj)
        self.__start_id = self.__end_id + 10
        self.__end_id += 20

        return obj

    def add_discipline_by_id(self, disc_id, disc_name):
        obj = Discipline(disc_id, disc_name)
        self.__discipline_list.append(obj)

    def add_discipline_undo(self, disc_id, name, pos):
        obj = Discipline(disc_id, name)
        self.__discipline_list.insert(pos, obj)

    def remove_discipline(self, discipline_id):
        """
        :param discipline_id: The id of the discipline we want to remove from repo (The name could be identical
                        other's, hence we know exactly which discipline by id)
        :return: Index
        """
        index = 0
        for disc in self.__discipline_list:
            current_id = int(disc.dis_id)
            if current_id == int(discipline_id):
                self.__discipline_list.remove(disc)
                return index
            index += 1

    def update_discipline(self, discipline_id, updated_name):
        """
        :param discipline_id: The id of the discipline we want to update
        :param updated_name: The name we want to give to this discipline
        :return: None
        """
        for i in range(len(self.__discipline_list)):
            if int(self.__discipline_list[i].dis_id) == int(discipline_id):
                self.__discipline_list[i].dis_name = updated_name

    def discipline_list(self):
        """
        :return: The current list
        """
        return self.__discipline_list

    def find_disc_by_id(self, id_disc):
        """
        :param id_disc: The discipline's id
        :return: The name of the discipline if the discipline exists, None otherwise
        """
        for i in range(len(self.__discipline_list)):
            if int(self.__discipline_list[i].dis_id) == int(id_disc):
                return self.__discipline_list[i].dis_name
        raise RepositoryErrors("The discipline from this id does not exist!")

    def __len__(self):
        return len(self.__discipline_list)

    def __getitem__(self, item):
        return self.__discipline_list[item]

    # WEEK 9
    def search_discipline(self, name):
        """
        Search for a discipline
        :param name: What we are searching for
        :return: A list containing all associations
        """
        # search_list = list()
        # found = False
        # if name == "":
        #     raise RepositoryErrors("Nothing to match!")
        #
        # for disc in self.__discipline_list:
        #     if name.lower() in disc.dis_name.lower():
        #         search_list.append([disc.dis_id, disc.dis_name])
        #         found = True
        #     elif str(name) in str(disc.dis_id):
        #         search_list.append([disc.dis_id, disc.dis_name])
        #         found = True

        def pass_function_discipline(element):
            if str(name).lower() in str(element.dis_name).lower():
                return True
            elif str(name).lower() in str(element.dis_id).lower():
                return True
            return False

        result = src.repository.collection.filter_data(self.__discipline_list, pass_function_discipline)
        if len(result) > 0:
            return result
        raise RepositoryErrors("Nothing to match!")

    def set_list(self, discipline_list):
        self.__discipline_list = discipline_list


class DisciplineFileTextRepository(DisciplineRepo):
    def __init__(self):
        super().__init__()
        self.file_text = "discipline.txt"
        self.load_file()

    def load_file(self):
        with open(self.file_text, "r") as f:
            for line in f.readlines():
                line = line.strip()
                line = line.replace("\n", "")
                if len(line) > 1:
                    disc_id, disc_name = line.split(",", maxsplit=1)
                    self.add_discipline_by_id(disc_id, disc_name)

    def save_file(self):
        with open(self.file_text, "w") as f:
            discipline_list = self.discipline_list()
            for disc in discipline_list:
                f.write(str(disc.dis_id) + "," + str(disc.dis_name) + "\n")

    def add_discipline(self, name):
        super(DisciplineFileTextRepository, self).add_discipline(name)
        self.save_file()

    def add_discipline_by_id(self, disc_id, disc_name):
        super(DisciplineFileTextRepository, self).add_discipline_by_id(disc_id, disc_name)
        self.save_file()

    def add_discipline_undo(self, disc_id, name, pos):
        super(DisciplineFileTextRepository, self).add_discipline_undo(disc_id, name, pos)
        self.save_file()

    def remove_discipline(self, discipline_id):
        aux = super(DisciplineFileTextRepository, self).remove_discipline(discipline_id)
        self.save_file()
        return aux

    def update_discipline(self, discipline_id, updated_name):
        super(DisciplineFileTextRepository, self).update_discipline(discipline_id, updated_name)
        self.save_file()


class DisciplineBinaryFileRepository(DisciplineRepo):
    def __init__(self):
        super().__init__()
        self.file_text = "discipline.bin"
        self.load_file()

    def load_file(self):
        with open(self.file_text, "rb") as f:
            try:
                self.data = pickle.load(f)
                self.set_list(self.data)
            except EOFError:
                self.data = list()

    def save_file(self):
        with open(self.file_text, "wb") as f:
            self.data = self.discipline_list()
            pickle.dump(self.data, f)

    def add_discipline(self, name):
        super(DisciplineBinaryFileRepository, self).add_discipline(name)
        self.save_file()

    def add_discipline_by_id(self, disc_id, disc_name):
        super(DisciplineBinaryFileRepository, self).add_discipline_by_id(disc_id, disc_name)
        self.save_file()

    def add_discipline_undo(self, disc_id, name, pos):
        super(DisciplineBinaryFileRepository, self).add_discipline_undo(disc_id, name, pos)
        self.save_file()

    def remove_discipline(self, discipline_id):
        aux = super(DisciplineBinaryFileRepository, self).remove_discipline(discipline_id)
        self.save_file()
        return aux

    def update_discipline(self, discipline_id, updated_name):
        super(DisciplineBinaryFileRepository, self).update_discipline(discipline_id, updated_name)
        self.save_file()


class DisciplineRepositoryTest(unittest.TestCase):
    def setUp(self):
        """
        Runs before any of the tests
        Used to set up the class so that tests can be run

        :return: None
        """
        self._repo = DisciplineRepo()
        self._discipline = Discipline('100', 'Maths')
        self._repo.add_discipline(self._discipline)

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



