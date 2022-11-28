#include "UserWindow.h"
#include "qmessagebox.h"
#include<sstream>

UserWindow::UserWindow(Service* serv, User u, QWidget *parent)
	: serv{ serv }, u{u}, QWidget(parent)
{
	ui.setupUi(this);
	this->connectSignalsAndSlots();
	this->populateQuestions();
}

void UserWindow::connectSignalsAndSlots()
{
	QObject::connect(ui.AQpushButton, &QPushButton::clicked, this, &UserWindow::addQButtonHandler);
	QObject::connect(ui.AApushButton, &QPushButton::clicked, this, &UserWindow::addAButtonHandler);
	QObject::connect(ui.listWidget->selectionModel(), &QItemSelectionModel::selectionChanged, this, &UserWindow::selectionHandler);
}

int UserWindow::getSelectedIndex()
{
	QModelIndexList selectedIndices = this->ui.listWidget->selectionModel()->selectedIndexes();
	if (selectedIndices.size() == 0)
	{
		return -1;
	}
	int selectedIndex = selectedIndices.at(0).row();
	return selectedIndex;
}

void UserWindow::populateQuestions()
{
	this->ui.listWidget->clear();
	std::vector<Question> data = this->serv->getAllQuest();

	for (auto obj : data)
	{
		QString string = QString::fromStdString(std::to_string(obj.getId()) + " " +  obj.getText() + " " + obj.getName());
		this->ui.listWidget->addItem(string);
	}
}

void UserWindow::update()
{
	this->populateQuestions();
}

void UserWindow::addQButtonHandler()
{
	std::string text = ui.QlineEdit->text().toStdString();
	if (text.empty())
	{
		QMessageBox msg;
		msg.setText("Invalid description!");
		msg.exec();
		return;
	}
	this->serv->addQuestion(text, u);

}

void UserWindow::addAButtonHandler()
{
	std::string text = ui.AlineEdit->text().toStdString();
	int index = this->getSelectedIndex();
	std::string s = ui.listWidget->currentItem()->text().toStdString();
	std::istringstream f(s);
	std::string qid;
	std::getline(f, qid);
	//int qId = this->serv->getAllQuest()[index].getId();
	int qId = stoi(qid);
	if (text.empty())
	{
		QMessageBox msg;
		msg.setText("Invalid description!");
		msg.exec();
		return;
	}
	this->serv->addAnswer(qId, text, u);
}

void UserWindow::selectionHandler()
{
	int index = this->getSelectedIndex();
	//std::string s = this->ui.listWidget->currentItem()->text().toStdString();
	//int qId = this->serv->getAllQuest()[index].getId();
	std::string s = ui.listWidget->currentItem()->text().toStdString();
	std::istringstream f(s);
	std::string qid;
	std::getline(f, qid);
	int qId = stoi(s);

	std::vector<Answer> a = this->serv->getAllAnsw();
	QListWidget* list = new QListWidget;

	for (auto obj : a)
	{
		if (obj.getIdQ() == qId)
		{
			QString string = QString::fromStdString(std::to_string(obj.getId()) + " " + obj.getText() + " " + obj.getName() + " "+
			std::to_string(obj.getNrVotes()));

			if (obj.getName() == u.getName())
			{
				QListWidgetItem* wi = new QListWidgetItem;
				wi->setText(string);
				wi->setBackground(Qt::yellow);
				list->addItem(wi);
			}
			else list->addItem(string);
		}
	}
	list->show();
}
