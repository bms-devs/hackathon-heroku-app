window.myScope = {
    rooms: []
}

function connect() {
    var socket = new SockJS('/websocketRegister');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/statusUpdate/update', function (updateMessage) {
            updateStatuses(JSON.parse(updateMessage.body))
        });
    });
}


var updateStatuses = function (result) {
    window.myScope.rooms = [];
    result.forEach(function (res) {
        window.myScope.rooms.push(res);
        var imgSrc;
        if (res.occupied === true) {
            imgSrc = "/circle_red.png";
        } else if (res.occupied === false) {
            imgSrc = "/circle_green.png";
        } else {
            imgSrc = "/unknown.png";
        }

        var roomEl = $("#" + res.id);
        roomEl.find("img").attr("src", imgSrc);
        roomEl.find(".status").text(res.status);
    })
}


function updateClocks() {
    for (var i = 0; i < window.myScope.rooms.length; i++) {
        var singleRoom = window.myScope.rooms[i];

        var diff = Math.round((new Date().getTime() - singleRoom.lastOccupiedStatusChangeDate) / 1000);

        var d = Math.floor(diff / (24 * 60 * 60));
        diff = diff - (d * 24 * 60 * 60);
        var h = Math.floor(diff / (60 * 60));
        diff = diff - (h * 60 * 60);
        var m = Math.floor(diff / (60));
        diff = diff - (m * 60);
        var s = diff;

        document.getElementById("timeSinceLastStatusChange" + singleRoom.id).innerHTML = "od " + h + " h " + m + " m, " + s + " s";
    }
}


setInterval(updateClocks, 1000);

connect();