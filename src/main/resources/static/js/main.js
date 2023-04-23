'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#core');

var stompClient = null;

var currentUserDiv = document.getElementById("current-user");
const username = currentUserDiv.dataset.userNickname;
var currentChatId = document.getElementById("current-chat-id");
const chatId = currentChatId.dataset.chatId;
var currentImageId = document.getElementById("current-link-img");
const imageId = currentImageId.dataset.linkImg;
function connect(username) {
    if(username) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}
function onConnected() {
    stompClient.subscribe('/topic/chat/' + chatId, onMessageReceived);
}

function onError(error) {

}
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            chatId: chatId
        };
        stompClient.send("/app/chat.sendMessage/" + chatId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('div');

    if (message.sender === username) {
        messageElement.classList.add('my-message');
    } else {
        messageElement.classList.add('interlocutor-message');
        var imgContainer = document.createElement('div');
        imgContainer.classList.add('mess-img');
        var imgElement = document.createElement('img');
        imgElement.setAttribute('src', '/images/' + imageId);
        imgElement.classList.add('round');
        imgContainer.appendChild(imgElement);
        messageElement.appendChild(imgContainer);
    }

    var messageContent = document.createElement('div');
    messageContent.classList.add('mess');

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageContent.appendChild(textElement);

    var timeElement = document.createElement('small');
    var time = new Date();
    var hours = time.getHours();
    var minutes = time.getMinutes();
    timeElement.innerText = hours + ":" + (minutes < 10 ? '0' : '') + minutes;
    messageContent.appendChild(timeElement);

    messageElement.appendChild(messageContent);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
function formatTime(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();

    if (hours < 10) {
        hours = '0' + hours;
    }

    if (minutes < 10) {
        minutes = '0' + minutes;
    }

    return hours + ':' + minutes;
}
connect(username)
messageForm.addEventListener('submit', sendMessage, true)