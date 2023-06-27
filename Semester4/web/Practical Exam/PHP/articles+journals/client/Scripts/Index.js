class HandleViewEvents {
    constructor () {
        const self = this;

        self.API = 'http://localhost:82/web-exam/server/Controllers';
        self.journals = [];

        self.index = -1;

        self.getArticles(self);

        self.getJournals(self);

        document.getElementById('btn-add').addEventListener('click', function () {
            self.handleAdd(self);
        });

        setInterval(function () {
            self.checkForNewJournals(self);
        }, 2000);
        
    }

    render(self, elements, containerId, ignored_keys = ['id']) {
        const mainContainer = document.getElementById(containerId);
    
        while (mainContainer.firstChild) {
            mainContainer.removeChild(mainContainer.firstChild);
        }

        elements.forEach(element => {
            const newRow = document.createElement('tr');

            for (const key in element) {
                if (ignored_keys.includes(key)) {
                    continue;
                }

                const newCell = document.createElement('td');
                newCell.innerHTML = element[key];
                newRow.appendChild(newCell);
            }

            if (containerId === 'journals') {

                if (self.index == element.id) {
                    newRow.classList.add('selected');
                }

                newRow.onclick = function () {
                    self.changeSelectedJournal(self, element.id);

                    const rows = document.getElementsByTagName('tr');

                    for (let i = 0; i < rows.length; i++) {
                        rows[i].classList.remove('selected');
                    }

                    newRow.classList.add('selected');
                };
            }

            mainContainer.appendChild(newRow);
        });
    }

    getJournals(self) {
        const request = new XMLHttpRequest();

        request.open('GET', `${self.API}/JournalController.php`);
        request.send();

        request.onload = function () {
            if (request.status == 200) {
                self.journals = JSON.parse(request.responseText);
                self.render(self, self.journals, 'journals');
                return;
            }

            console.log('Error: ' + request.status);
        }
    }

    changeSelectedJournal(self, journalId) {
        self.index = journalId;

        const request = new XMLHttpRequest();

        const user = localStorage.getItem("user");

        request.open('GET', `${self.API}/ArticleController.php?user=${user}&journalId=${journalId}`);
        request.send();

        request.onload = function () {
            if (request.status != 200) {
                console.log("Error: " + request.status);
                return;
            }

            self.my_articles = JSON.parse(request.responseText);
            self.render(self, self.my_articles, 'my-articles', ['id', 'user', 'journalId']);
        }
    }

   

    handleAdd(self) {
        const request = new XMLHttpRequest();

        const user = localStorage.getItem("user");
        const journalName = document.getElementById('journal-name').value;
        const summary = document.getElementById('summary').value;

        request.open('POST', `${self.API}/ArticleController.php`);
        request.setRequestHeader('Content-Type', 'application/json');
    
        const data = JSON.stringify({
            "user": user,
            "journalName": journalName,
            "summary": summary
        });

        request.send(data);

        request.onload = function () {
            if (request.status != 200) {
                console.log("Error: " + request.status);
                return;
            }

            self.getJournals(self);
            self.changeSelectedJournal(self, self.index);
            self.getArticles(self);
        }
    }

    getArticles(self) {
        const request = new XMLHttpRequest();

        request.open('GET', `${self.API}/ArticleController.php`);
        request.send();

        request.onload = function () {
            if (request.status != 200) {
                console.log("Error: " + request.status);
                return;
            }

            self.articles = JSON.parse(request.responseText);
        }
    }

    checkForNewJournals(self) {        
        const request = new XMLHttpRequest();

        request.open('GET', `${self.API}/ArticleController.php`);
        request.send();

        request.onload = function () {
            if (request.status != 200) {
                console.log('Error: ' + request.status);
                return;
            }
            
            const articles = JSON.parse(request.responseText);

            const difference = articles.filter(x => {
                for (let i = 0; i < self.articles.length; i++) {
                    if (self.articles[i].id == x.id) {
                        return false;
                    }
                }
                return true;
            });

            if (difference.length == 0) {
                return;
            }

            const user = localStorage.getItem("user");
            for (let i = 0; i < difference.length; i++) {
                if (difference[i].user != user) {
                    window.alert("New articles found!");
                    self.articles = articles;
                    return;
                }
            }
        }
    }
}