#include "ProgrammerWindow.h"
#include <QtWidgets/QApplication>

#include "Service.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    
    std::string filePathP = "programmers.txt";
    std::string filePathS = "sourceFile.txt";

    Repository<Programmer>* pr = new Repository<Programmer>(filePathP);
    Repository<SourceFile>* s = new Repository<SourceFile>(filePathS);

    std::ifstream finP(filePathP);
    Programmer p;
    Service* service = new Service{pr, s};

    while (finP >> p)
    {
        ProgrammerWindow* w = new ProgrammerWindow(service, p);
        w->setWindowTitle(QString::fromStdString(p.getName()));
        service->addObserver(w);
        w->show();
    }

    return a.exec();
}
