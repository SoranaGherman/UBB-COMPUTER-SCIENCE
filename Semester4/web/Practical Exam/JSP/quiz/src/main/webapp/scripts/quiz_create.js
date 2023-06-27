// script.js
function generateInputFields() {
    var numInputs = document.getElementById('numInputs').value;
    var inputContainer = document.getElementById('inputContainer');

    // Clear any existing input fields
    inputContainer.innerHTML = '';

    // Generate new input fields
    for (var i = 0; i < numInputs; i++) {
        var input = document.createElement('input');
        input.type = 'text';
        input.name = 'inputField' + i;
        inputContainer.appendChild(input);
        inputContainer.appendChild(document.createElement('br'));
    }
}
