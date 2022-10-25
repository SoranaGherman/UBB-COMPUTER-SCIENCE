from src.repository.disc_repo import DisciplineRepo, DisciplineFileTextRepository, DisciplineBinaryFileRepository
from src.repository.grade_repo import GradeRepository, GradeFileTextRepository, GradeBinaryFileRepository
from src.repository.stud_repo import StudentRepository, StudentFileTextRepository, StudentBinaryFileRepository
from src.services.student_services import StudentCtrl
from src.services.discipline_services import DisciplineCtrl
from src.services.grade_services import GradeCtrl
from src.services.undo_service import UndoCtrl
from src.validations.val import ValidationErrors
from src.errors.error import RepositoryErrors


class Ui:
    def __init__(self):
        self._undo_ctrl = UndoCtrl()
        stud_repo, disc_repo, grade_repo = settings_properties()
        self._stud_ctrl = StudentCtrl(self._undo_ctrl, stud_repo)
        self._disc_ctrl = DisciplineCtrl(self._undo_ctrl, disc_repo)
        self._grade_ctrl = GradeCtrl(self._stud_ctrl, self._disc_ctrl, self._undo_ctrl, grade_repo)

    @staticmethod
    def _main_menu():
        print()
        print("1. Display the list of students")
        print("2. Display the list of disciplines")
        print("3. Add student")
        print("4. Add discipline")
        print("5. Remove student")
        print("6. Remove discipline")
        print("7. Update students")
        print("8. Update disciplines")
        print("9. Grade student")
        print("10. Print the grade list")
        print("11. Search a student")        # FILTER DONE
        print("12. Search a discipline")     # FILTER DONE
        print("13. All students failing at one or more disciplines (students having an average <5 for"
              " a discipline are failing it)")    # FILTER DONE
        print("14. Students with the best school situation, sorted in descending order of their aggregated average "
              "(the average between their average grades per discipline)")  # SORT
        print("15. All disciplines at which there is at least one grade, "
              "sorted in descending order of the average grade(s) received by all students")  # SORT DONE + FILTER DONE
        print("16. Undo")
        print("17. Redo")
        print("18. Exit")
        print()

    def _print_students(self):
        display_list = self._stud_ctrl.student_list()
        for student in display_list:
            print(student)

    def _print_disciplines(self):
        display_list = self._disc_ctrl.discipline_list()
        for disc in display_list:
            print(disc)

    def _add_student_ui(self):
        name = input("Enter a name: ")
        try:
            self._stud_ctrl.add_student(name)
        except ValidationErrors as ve:
            print(str(ve))

    def _add_discipline_ui(self):
        name = input("Enter a discipline: ")
        try:
            self._disc_ctrl.add_discipline(name)
        except ValidationErrors as ve:
            print(str(ve))

    def _remove_student_ui(self):
        stud_id = input("Enter the id of the student you want to remove: ")
        try:
            self._grade_ctrl.remove_student_grades(stud_id)
            # self._stud_ctrl.remove_student(stud_id)
        except RepositoryErrors as er:
            print(str(er))

    def _update_student_ui(self):
        stud_id = input("Enter the id of the student you want to update: ")
        stud_name = input("Enter the new name of the student from this id: ")
        try:
            self._stud_ctrl.update_student(stud_id, stud_name)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def _remove_discipline_ui(self):
        try:
            disc_id = input("Enter the id of the discipline you want to remove: ")
            self._grade_ctrl.remove_discipline(disc_id)
            # self._disc_ctrl.remove_discipline(disc_id)
        except RepositoryErrors as er:
            print(str(er))

    def _update_discipline_ui(self):
        disc_id = input("Enter the id of the discipline you want to update: ")
        disc_name = input("Enter the new name of the discipline from this id: ")
        try:
            self._disc_ctrl.update_discipline(disc_id, disc_name)
        except RepositoryErrors as re:
            print(str(re))
        except ValidationErrors as va:
            print(str(va))

    def _grade_student_ui(self):
        stud_id = input("Enter the id of the student you want to grade: ")
        disc_id = input("Enter the id of the discipline you want to grade the student: ")
        grade_value = input("Enter the grade you want to give the student at this discipline: ")
        try:
            if self._stud_ctrl.find_stud_by_id(stud_id) and self._disc_ctrl.find_disc_by_id(disc_id):
                self._grade_ctrl.add_grade(stud_id, disc_id, grade_value)
        except RepositoryErrors as err:
            print(str(err))
        except ValidationErrors as va:
            print(str(va))

    def _print_grade_list_ui(self):
        try:
            display_list = list()
            grade_list = self._grade_ctrl.grade_list()
            for grade in grade_list:
                name = self._stud_ctrl.find_stud_by_id(grade.stud_id)
                discipline = self._disc_ctrl.find_disc_by_id(grade.disc_id)
                display_list.append([grade.stud_id, name, grade.disc_id, discipline,
                                     grade.grade_value])

            display_list.sort(key=lambda x: int(x[0]))
            for st in display_list:
                print("ID:" + str(st[0]) + " Student " + str(st[1]) + " has the grade " + str(st[4]) + " at " + str(st[3]) +
                      " ID:" + str(st[2]))
        except Exception as e:
            print(str(e))

    # WEEK 9
    def _print_search_stud_ui(self):
        try:
            name = input("Enter a name or id to use for searching a student: ")
            display_list = self._stud_ctrl.search_student(name)
            for stud in display_list:
                print("ID: " + str(stud.stud_id) + " Student " + str(stud.stud_name))
        except RepositoryErrors as re:
            print(str(re))

    def _print_search_discipline_ui(self):
        try:
            name = input("Enter a name or id to use for searching a discipline: ")
            display_list = self._disc_ctrl.search_discipline(name)
            for disc in display_list:
                print("ID: " + str(disc.dis_id) + " Discipline " + str(disc.dis_name))
        except RepositoryErrors as re:
            print(str(re))

    def _print_discipline_average_grade(self):
        try:
            display_list = self._grade_ctrl.average_grade_discipline()
            display_list = self._grade_ctrl.sort_by_average_discipline(display_list)
            # display_list.sort(key=lambda x: x[2], reverse=True)
            for disc in display_list:
                print("ID:" + str(disc[0]) + " Discipline " + str(disc[1]) + ". The average grade(s) received by all "
                                                                             "students: " + str(disc[2]))

        except RepositoryErrors as re:
            print(str(re))

    def _print_students_fail_discipline_ui(self):
        try:
            display_list = self._grade_ctrl.student_fails_discipline()
            display_list.sort(key=lambda x: x[0])
            for stud in display_list:
                print("ID:" + str(stud[0]) + " Student " + str(stud[1]) + " failed")
        except RepositoryErrors as re:
            print(str(re))

    def _print_students_situation_ui(self):
        try:
            display_list = self._grade_ctrl.students_situation()
            display_list = self._grade_ctrl.sort_by_average_situation(display_list)
            # display_list.sort(key=lambda x: x[1], reverse=True)
            for stud in display_list:
                print("ID:" + str(stud[0]) + " Student " + str(stud[2]) + " has an aggregate average of " +
                      str(stud[1]))
        except RepositoryErrors as re:
            print(str(re))

    def start(self):
        while True:
            try:
                self._main_menu()
                opt = input("Enter a command: ")
                if opt == "1":
                    self._print_students()
                elif opt == "2":
                    self._print_disciplines()
                elif opt == "3":
                    self._add_student_ui()
                elif opt == "4":
                    self._add_discipline_ui()
                elif opt == '5':
                    self._remove_student_ui()
                elif opt == '6':
                    self._remove_discipline_ui()
                elif opt == '7':
                    self._update_student_ui()
                elif opt == '8':
                    self._update_discipline_ui()
                elif opt == '9':
                    self._grade_student_ui()
                elif opt == '10':
                    self._print_grade_list_ui()
                elif opt == '11':
                    self._print_search_stud_ui()
                elif opt == '12':
                    self._print_search_discipline_ui()
                elif opt == '13':
                    self._print_students_fail_discipline_ui()
                elif opt == '14':
                    self._print_students_situation_ui()
                elif opt == '15':
                    self. _print_discipline_average_grade()
                elif opt == '16':
                    try:
                        self._stud_ctrl.undo()
                    except Exception as ex:
                        print(str(ex))
                elif opt == '17':
                    try:
                        self._stud_ctrl.redo()
                    except Exception as ex:
                        print(str(ex))
                elif opt == "18":
                    return
                else:
                    print("Bad selection!")

            except ValueError as ve:
                print("Make sure the id s are integers!")
            # except IndexError as er:
            #     print(str(er))


def settings_properties():
    file_name = "settings.properties"
    stud_repo = None
    disc_repo = None
    grade_repo = None

    with open(file_name, "r") as f:
        line = f.readline()
        line = line.strip("\n")
        repo, type = line.split("=")

        if type == 'in_memory':
            stud_repo = StudentRepository()
            disc_repo = DisciplineRepo()
            grade_repo = GradeRepository()

        elif type == 'text':
            for line in f.readlines():
                line = line.strip()
                line = line.strip("\n")

                repo, name = line.split("=")
                if repo == "student_file":
                    stud_repo = StudentFileTextRepository()
                elif repo == "discipline_file":
                    disc_repo = DisciplineFileTextRepository()
                elif repo == "grade_file":
                    grade_repo = GradeFileTextRepository()

        elif type == 'binary':
            for line in f.readlines():
                line = line.strip()
                line = line.strip("\n")

                repo, name = line.split("=")
                if repo == "student_file":
                    stud_repo = StudentBinaryFileRepository()
                elif repo == "discipline_file":
                    disc_repo = DisciplineBinaryFileRepository()
                elif repo == "grade_file":
                    grade_repo = GradeBinaryFileRepository()

    return stud_repo, disc_repo, grade_repo


console = Ui()
console.start()


