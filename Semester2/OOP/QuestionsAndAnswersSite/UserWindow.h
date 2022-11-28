#pragma once

#include <QWidget>
#include "ui_UserWindow.h"
#include "Service.h"

class UserWindow : public QWidget, public Observer
{
	Q_OBJECT

public:
	UserWindow(Service* serv, User u, QWidget *parent = Q_NULLPTR);
	~UserWindow() = default;

	void connectSignalsAndSlots();
	int getSelectedIndex();
	void populateQuestions();
	void update();
	void addQButtonHandler();
	void addAButtonHandler();
	void selectionHandler();

private:
	Ui::UserWindow ui;
	Service* serv;
	User u;
};
