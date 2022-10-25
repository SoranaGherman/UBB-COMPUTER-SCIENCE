from src.domain.student import Student
from src.errors.error import ValidationErrors


class ValidationStudents(object):
    def validation_stud(self, student_name):
        errors = ""
        if student_name == "":
            errors += "Invalid name!\n"
        if len(errors) > 0:
            raise ValidationErrors(errors)


class ValidationDisciplines:
    def validation_disc(self, discipline_name):
        errors = ""
        if discipline_name == "":
            errors += "Invalid discipline!\n"
        if len(errors) > 0:
            raise ValidationErrors(errors)


class GradeValidation(object):
    def validation_grade(self, grade):
        errors = ""
        if grade == "":
            errors += "Invalid grade!\n"
        elif float(grade) <= 0 or float(grade) > 10:
            errors += "The grade must be a value between 1 and 10!\n"
        if len(errors) > 0:
            raise ValidationErrors(errors)

