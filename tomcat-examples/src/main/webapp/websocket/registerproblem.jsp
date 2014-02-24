<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Datos del Problema</title>
</head>
<body>

	<form action="problemDetail" method="post">
		<table>
			<tr>
				<td>
					<label>Modelo:</label>
				</td>
				<td>
					<input name="modelo"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>N° Serie:</label>
				</td>
				<td>
					<input name="serie"/>
				</td>
			</tr>
			<tr>
				<td>
					<label>Problema:</label>
				</td>
				<td>
					<textarea name="prolema" rows="10" cols="20"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Iniciar Conversacion">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>