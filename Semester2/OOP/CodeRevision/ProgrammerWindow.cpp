#include "ProgrammerWindow.h"

ProgrammerWindow::ProgrammerWindow(Service* service, Programmer p, QWidget* parent)
	: service{ service }, p{ p }, QWidget(parent)
{
	ui.setupUi(this);
	this->populateList();
	this->connectSignalsAndSlots();
	this->ui.revisePushButton->setEnabled(true);
}


ProgrammerWindow::~ProgrammerWindow()
{
}

void ProgrammerWindow::connectSignalsAndSlots()
{
	QObject::connect(ui.AddpushButton, &QPushButton::clicked, this, &ProgrammerWindow::addButtonHandler);
	QObject::connect(ui.revisePushButton, &QPushButton::clicked, this, &ProgrammerWindow::updateStatus);
	QObject::connect(ui.listWidget, &QListWidget::itemSelectionChanged, this, &ProgrammerWindow::indexHandler);
}

void ProgrammerWindow::populateList()
{
	this->ui.listWidget->clear();
	std::vector<SourceFile> data = this->service->getAllSourceFiles();
	int index = 0;

	for (auto obj : data)
	{
		QString string = QString::fromStdString(obj.getName() + " " + obj.getStatus() + " " + obj.getCreator() + " " + obj.getReviewer());
			
		this->ui.listWidget->addItem(string);
		if (obj.getStatus() == "not_revised")
		{
			QFont font;
			font.setBold(true);
			this->ui.listWidget->item(index)->setFont(font);
		}

		index++;
	}

	this->ui.labelHR->setText(QString::number(p.getNrOfRF()));
	this->ui.labelSHR->setText(QString::number(p.getNrOfRFMust() - p.getNrOfRF()));
}

void ProgrammerWindow::addButtonHandler()
{
	std::string filename = ui.addLineEditbu->text().toStdString();
	if (filename.empty())
	{
		QMessageBox text;
		text.setText("FileName empty!");
		text.exec();

	}
	else
	{
			this->service->addFile(filename, p);
	}
}

int ProgrammerWindow::getSelectedIndex()
{
	QModelIndexList selectedIndices = this->ui.listWidget->selectionModel()->selectedIndexes();
	if (selectedIndices.size() == 0)
	{
		return -1;
	}
	int selectedIndex = selectedIndices.at(0).row();
	return selectedIndex;
}

void ProgrammerWindow::indexHandler()
{
	int index = getSelectedIndex();
	std::string  status = this->service->getAllSourceFiles()[index].getStatus();
	if (status == "not_revised")
		this->ui.revisePushButton->setEnabled(true);
	else this->ui.revisePushButton->setDisabled(true);
}

void ProgrammerWindow::updateStatus()
{
	int index = getSelectedIndex();
	std::string  status = this->service->getAllSourceFiles()[index].getStatus();

	this->service->updateStatus(index, p);

	if (p.getNrOfRF() == p.getNrOfRFMust())
	{
		QMessageBox msg;
		msg.setText("Congratulations!");
		msg.exec();
	}
	
}

void ProgrammerWindow::update()
{
	this->populateList();
}
