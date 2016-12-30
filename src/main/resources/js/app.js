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
    })
}



connect();