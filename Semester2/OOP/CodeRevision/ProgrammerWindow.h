#pragma once

#include <QWidget>
#include "ui_ProgrammerWindow.h"
#include "Service.h"
#include "Observer.h"
#include "qmessagebox.h"

class ProgrammerWindow : public QWidget, public Observer
{
	Q_OBJECT

public:
	ProgrammerWindow(Service* service, Programmer p, QWidget *parent = Q_NULLPTR);
	~ProgrammerWindow();

	void connectSignalsAndSlots();
	void populateList();
	void addButtonHandler();
	int getSelectedIndex();
	void indexHandler();
	void updateStatus();
	void update() override;
	
private:
	Ui::ProgrammerWindow ui;
	Service* service;
	Programmer p;
};
