class HandleViewEvents {
    constructor () {
        const self = this;

        self.API = 'http://localhost:82/web-exam/server/Controllers';

        document.getElementById('btn-login').addEventListener('click', function () {
            self.login(self);
        });

        console.log('hello');
    }

    login(self) {
        const request = new XMLHttpRequest();

        const user = document.getElementById('user-name').value;

        request.open('POST', `${self.API}/ArticleController.php?login=${user}`);
        request.send();

        request.onload = function () {
            const response = request.responseText;

            if (response == 1) {
                window.location.href = `./index.html`;
                localStorage.setItem('user', user);
                window.alert(`Welcome, ${user}!`)
                return;
            }

            if (response == 0) {
                window.alert('Invalid username');
                return;
            }

            window.alert('Something went wrong');
        }
    }
}