var lastUpdateTime1 = 0;

function connect() {
    var socket = new SockJS('/websocketRegister');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/statusUpdate/update', function(updateMessage) {
            updateStatuses(JSON.parse(updateMessage.body))
        });
    });
}

var startStamp1;
var startStamp2;

var updateStatuses = function(result) {
    result.forEach(function(res) {
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
        if (res.id == 1) {
            startStamp1 = res.lastUpdateDate
        } else if (res.id == 2) {
            startStamp2 = res.lastUpdateDate
        }
    })
}

function updateClock1() {
    newDate = new Date();
    newStamp = newDate.getTime();
    if (!startStamp1) {
        startStamp1 = newStamp
    }
    var diff = Math.round((newStamp - startStamp1) / 1000);

    var d = Math.floor(diff / (24 * 60 * 60));
    diff = diff - (d * 24 * 60 * 60);
    var h = Math.floor(diff / (60 * 60));
    diff = diff - (h * 60 * 60);
    var m = Math.floor(diff / (60));
    diff = diff - (m * 60);
    var s = diff;

    document.getElementById("lastUpdateDate1").innerHTML = h + " h " + m + " m, " + s + " s";
}

function updateClock2() {
    newDate = new Date();
    newStamp = newDate.getTime();
    if (!startStamp2) {
        startStamp2 = newStamp
    }
    var diff = Math.round((newStamp - startStamp2) / 1000);

    var d = Math.floor(diff / (24 * 60 * 60));
    diff = diff - (d * 24 * 60 * 60);
    var h = Math.floor(diff / (60 * 60));
    diff = diff - (h * 60 * 60);
    var m = Math.floor(diff / (60));
    diff = diff - (m * 60);
    var s = diff;

    document.getElementById("lastUpdateDate2").innerHTML = h + " h " + m + " m, " + s + " s";
}

setInterval(updateClock1, 1000);
setInterval(updateClock2, 1000);


connect();