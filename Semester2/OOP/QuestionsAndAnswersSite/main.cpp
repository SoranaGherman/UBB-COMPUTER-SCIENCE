#include <QtWidgets/QApplication>
#include "UserWindow.h"
#include "SearchWindow.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    std::string filePathA = "file3.txt";
    std::string filePathQ = "file2.txt";

    Repository<Question>* qR = new Repository<Question>{filePathQ};
    Repository<Answer>* aR = new Repository<Answer>{ filePathA };

    Service* serv = new Service{qR, aR};

    std::ifstream finU("file1.txt");
    User u;

    while (finU >> u)
    {
        UserWindow* window = new UserWindow{serv, u};
        window->setWindowTitle(QString::fromStdString(u.getName()));
        serv->addObserver(window);
        window->show();
    }

    SearchWindow* w = new SearchWindow{serv};
    serv->addObserver(w);
    w->show();
    
    return a.exec();
}
