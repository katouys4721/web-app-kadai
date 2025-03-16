<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE>
<html lang="ja">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>商品管理アプリ</title>
	<link rel="stylesheet"
		href="<%= request.getContextPath() %>/css/style.css">
	<%--Google Fontsの読み込み --%>
	<link rel="preconnect" href="https://fonts.googleeapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
    rel="stylesheet">
</head>

<body>
	<header>
		<nav>
			<a href="<%= request.getContextPath() %>/">商品管理アプリ</a>
		</nav>
	</header>
	
	<main>
		<article class="home">
			<h1>商品管理アプリ</h1>
			<p>『Javaとデータベースで商品管理アプリを作ろう』成果物</p>
			<a href="<%= request.getContextPath() %>/list" class="btn">商品一覧</a>
		</article>
	</main>
	
	<footer>
		<p class="copyright">&copy; 商品管理アプリ All right reserved.</p>
	</footer>
</body>
</html>