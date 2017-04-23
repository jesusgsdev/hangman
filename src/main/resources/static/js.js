/**
 * Created by jesgarsal on 23/04/17.
 */
$(document).ready( function () {

    alreadyLoadedTable = false;
    currentUserName = 'none';
    currentGame = '';

    $('#login').click(function(e){
        e.preventDefault();

        currentUserName = $('#username').val();
        $.ajax({
            url: '/login/' + currentUserName,
            type: 'GET',
            dataType: 'text',
            timeout: 30000,
            success: function(data) {
                var typeOfUser = data;
                var userWelcome = 'Welcome ' + currentUserName
                $('#loginForm').hide();
                $('#userWelcome h3').text(userWelcome);
                $('#userWelcome, #logoutForm').show();
                if(typeOfUser === 'MANAGER'){
                    $('#managerView').show();
                    updateManagementTable();
                }else if(typeOfUser === 'PLAYER'){
                    $('#managerView').hide();
                    $('#startGame').show();
                }
            }
        });
    });

    $(document).on('click', '.characterToPlay', function () {
        var characterChosen = $(this).val();
        $.ajax({
            url: '/game/update/' + currentUserName + '/' + characterChosen,
            type: 'GET',
            dataType: 'json',
            timeout: 30000,
            success: function(data) {
                currentGame = data;
                loadCurrentGame();
            }
        });
    });

    $('#startGame').click(function(e){
        e.preventDefault();

        $('#secretWord').hide();
        $.ajax({
            url: '/game/' + currentUserName,
            type: 'GET',
            dataType: 'json',
            timeout: 30000,
            success: function(data) {
                currentGame = data;
                $('#gameView').show();
                loadCurrentGame();
            }
        });
    });

    $('#logout').click(function(e){
        e.preventDefault();

        $('#loginForm').show();
        $('#logoutForm, #managerView, #userWelcome, #startGame, #gameView').hide();

    });
});

function loadCurrentGame() {
    var visibleWord = currentGame.visibleWord,
        attemptsLeft = currentGame.attemptsLeft,
        availableCharacters = currentGame.availableCharacters,
        status = currentGame.status,
        secretWord = currentGame.secretWord;


    $('#visibleWord').text(visibleWord);
    $('#status').text(status);
    $('#attemptsLeft').text(attemptsLeft);
    $('#availableCharacters').removeClass('alert alert-warning');
    $('#availableCharacters').empty();

    if(status === 'ACTIVE') {
        printOutAvailableCharacters(availableCharacters);
    }else{
        $('#availableCharacters').addClass('alert alert-warning');
        $('#availableCharacters').text("You cannot keep playing. Please, click on Start/Continue Game.");
        if(status === 'LOST') {
            $('#secretWord').show();
            $('#secretWord').text("The secret word was " + secretWord);
        }
    }
}

function printOutAvailableCharacters(availableCharacters) {
    $(availableCharacters).each(function () {
        var letter = $(this)[0],
            button = '<input class=\"characterToPlay\" type=\"button\" name=\"' + letter + '\" id=\"' + letter + '\" value=\"' + letter + '\">';
        $('#availableCharacters').append(button);
    });
}

function updateManagementTable(){
    if(alreadyLoadedTable === false) {
        var table = $('#employeesTable').DataTable({
            "sAjaxSource": "/management/",
            "sAjaxDataProp": "",
            "order": [[0, "asc"]],
            "aoColumns": [
                {"mData": "username"},
                {"mData": "secretWord"},
                {"mData": "visibleWord"},
                {"mData": "attemptsLeft"},
                {"mData": "status"},
                {"mData": "startDate"},
                {"mData": "endDate"}
            ]
        });
    }
    alreadyLoadedTable = true;
}