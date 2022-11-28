#pragma once

#include <QWidget>
#include "ui_SearchWindow.h"
#include "Service.h"

class SearchWindow : public QWidget, public Observer
{
	Q_OBJECT

public:
	SearchWindow(Service* serv, QWidget *parent = Q_NULLPTR);
	~SearchWindow();

	void connnectSignalsAndSlots();
	void populateList();
	void update();

private:
	Ui::SearchWindow ui;
	Service* serv;
};
