
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	System.out.println("Validating user");
	if (request.getSession().getAttribute("user") == null) {
		request.getRequestDispatcher("index.jsp").forward(request,
				response);
	}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Apache Tomcat WebSocket Examples: Chat</title>
<style type="text/css">
input#chat {
	width: 410px
}

#console-container {
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
<script type="text/javascript">
	//	function load() {
	var Chat = {};

	Chat.socket = null;

	Chat.connect = (function(host) {
		if ('WebSocket' in window) {
			Chat.socket = new WebSocket(host);
		} else if ('MozWebSocket' in window) {
			Chat.socket = new MozWebSocket(host);
		} else {
			Console.log('Error: WebSocket is not supported by this browser.');
			return;
		}

		Chat.socket.onopen = function() {
			Console.log('Info: WebSocket connection opened.');
			document.getElementById('chat').onkeydown = function(event) {
				if (event.keyCode == 13) {
					Chat.sendMessage();
				}
			};
		};

		Chat.socket.onclose = function() {
			document.getElementById('chat').onkeydown = null;
			Console.log('Info: WebSocket closed.');
		};

		Chat.socket.onmessage = function(message) {
			Console.log(message.data);
		};
	});

	Chat.initialize = function() {
		if (window.location.protocol == 'http:') {
			Chat.connect('ws://' + window.location.host
					+ '/my-examples/websocket/chat');
		} else {
			Chat.connect('wss://' + window.location.host
					+ '/my-examples/websocket/chat');
		}
	};

	Chat.sendMessage = (function() {
		console.log('it was called');
		var message = document.getElementById('chat').value;
		if (message != '') {
			Chat.socket.send(message);
			document.getElementById('chat').value = '';
		}
	});

	var Console = {};

	Console.log = (function(message) {
		var console = document.getElementById('console');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.innerHTML = message;
		console.appendChild(p);
		while (console.childNodes.length > 25) {
			console.removeChild(console.firstChild);
		}
		console.scrollTop = console.scrollHeight;
	});

	Chat.initialize();

	document.addEventListener("DOMContentLoaded", function() {
		// Remove elements with "noscript" class - <noscript> is not allowed in XHTML
		var noscripts = document.getElementsByClassName("noscript");
		for (var i = 0; i < noscripts.length; i++) {
			noscripts[i].parentNode.removeChild(noscripts[i]);
		}
	}, false);

	function sendMyMessage() {
		Chat.socket.send('This is an introductory message');
	}

	//	};
</script>
</head>
<!-- <body onload="load()"> -->
<body>
	<table>
		<tr>
			<td>
				${user}
				<div>
					<p>
						<input type="text" placeholder="type and press enter to chat"
							id="chat" />
					</p>
					<div id="console-container">
						<div id="console" />
					</div>
				</div>
				<form action="login" method="post">
					<input type="hidden" name="action" value="logout"> <input
						value="Logout" type="submit" />
				</form>
			</td>
			<c:if test="${user.type eq 'H'}">
			<td>
					<table>
						<tr>
							<td>
								<button id="presentation" onclick="sendMyMessage()">Presentacion</button>
							</td>
						</tr>
						<tr>
							<td>
								<button>Dispositivo</button>
							</td>
						</tr>
						<tr>
							<td>
								<button>Registro Disp.</button>
							</td>
						</tr>
						<tr>
							<td>
								<button>Reg. Problema</button>
							</td>
						</tr>
						<tr>
							<td>
								<button>Ayuda</button>
							</td>
						</tr>
					</table>
			</td>
			<td>
				<table>
					<tr>
						<td>
							<input placeholder="N° Serie">
							<input placeholder="Codigo">
							<input placeholder="Modelo">
						</td>
					</tr>
					<tr>
						<td>
							<input placeholder="Garantia">
						</td>
					<tr>
						<td>
						<select>
							<option>Problemas</option>
						</select>
						</td>
					<tr>
						<td>
							<select multiple="multiple" title="Soluciones">
								<option>option1</option>
							</select>
						</td>
					</tr>
				</table>
				
			</td>
			</c:if>
		</tr>
	</table>
</body>
</html>
