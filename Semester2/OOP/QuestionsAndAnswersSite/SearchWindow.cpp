#include "SearchWindow.h"
#include <string>

SearchWindow::SearchWindow(Service* serv, QWidget *parent)
	: serv{serv}, QWidget(parent)
{
	ui.setupUi(this);
	this->connnectSignalsAndSlots();
}

SearchWindow::~SearchWindow()
{
}

void SearchWindow::connnectSignalsAndSlots()
{
	QObject::connect(ui.lineEdit, &QLineEdit::textChanged, this, &SearchWindow::populateList);
}

void SearchWindow::populateList()
{
	this->ui.listWidget->clear();
	std::vector<Question> q = this->serv->getAllQuest();
	std::string t = this->ui.lineEdit->text().toStdString();

	for (auto obj : q)
	{
		std::string s = std::to_string(obj.getId()) + " " + obj.getText() + " " + obj.getName();
		if (s.find(t))
			this->ui.listWidget->addItem(QString::fromStdString(s));
	}

}

void SearchWindow::update()
{
	this->populateList();
}
