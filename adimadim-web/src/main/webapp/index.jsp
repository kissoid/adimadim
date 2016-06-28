<%-- 
    Document   : index
    Created on : May 10, 2014, 2:01:12 PM
    Author     : Ergo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Adım Adım Koşu</title>
        <link rel="SHORTCUT ICON" href="/resource/image/favicon.ico" />
        <link rel="stylesheet" href="/resource/css/foundation.css" />
        <link rel="stylesheet" href="/resource/css/main.css" />
        <script src="/resource/js/vendor/modernizr.js"></script>
        <style type="text/css">
#overlay {
     visibility: hidden;
     position: absolute;
     left: 0px;
     top: 0px;
     width:100%;
     height:100%;
     text-align:center;
     z-index: 1000000;
}

#overlay div {
     width:500px;
     height: 500px;
     margin: 170px auto;
     background-color: #fff;
     border:1px solid #000;
     padding:15px;
     text-align:center;
}
        </style>
    </head>
    <body class="antialiased no-bg">
        <hr class="gradient-bar" />
        <a href="" class="menu-toggle">
            <img src="/resource/image/hamburger.png" width="24" />    
        </a>
        <a href="/outsession/dagi/adimadim.jsf">
            <img src="/resource/image/aa-icon.png" class="aa-logo" />
        </a>
        <img src="/resource/image/aakosu-big-logo.png" alt="Adım Adım Koşu" class="cover-logo" />

        <ul class="example-orbit" data-orbit="" data-options="animation: fade; circular: true; slide_number: false; navigation_arrows: false; timer_show_progress_bar: false; timer_speed: 3000; pause_on_hover: false;">
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
            <li style="background-image: url('/resource/image/cover1.jpg');"></li>
            <li style="background-image: url('/resource/image/cover2.jpg');"></li>
            <li style="background-image: url('/resource/image/cover3.jpg');"></li>
        </ul>

        <nav class="cover">
            <div class="midway">
                <a href="/outsession/dagi/about.jsf">
                    <img src="/resource/image/about-icon.png" />
                    <span>Nedir?</span>
                </a>
                <a href="/outsession/dagi/next-run.jsf">
                    <img src="/resource/image/run-icon.png" />
                    <span>Koşular</span>
                </a>
                <a href="/outsession/dagi/location.jsf">
                    <img src="/resource/image/location-icon.png" />
                    <span>Nerede?</span>
                </a>
                <a href="/outsession/dagi/join.jsf">
                    <img src="/resource/image/join-icon.png" />
                    <span>Kayıt Ol</span>
                </a>
                <a href="/insession/dagi/team-list.jsf">
                    <img src="/resource/image/team-icon.png" />
                    <span>Takım Kur</span>
                </a>
                <a href="/insession/BipNumberServlet">
                    <img src="/resource/image/barcode-icon.png" />
                    <span>Göğüs No</span>
                </a>
                <a href="/outsession/dagi/faq.jsf">
                    <img src="/resource/image/faq-icon.png" />
                    <span>Sorular</span>    
                </a>    
            </div>
        </nav>
<div id="overlay">
     <div>
         <p><img src="/resource/image/kosu-iptal.png" /></p>
     </div>
</div>
        <script src="/resource/js/vendor/jquery.js"></script>
        <script src="/resource/js/foundation.min.js"></script>
        <script src="/resource/js/vendor/midway.min.js"></script>
        <script>
            $(document).foundation();
            $('a.menu-toggle').on('click', function (e) {
                $('nav.cover').fadeToggle();
                e.preventDefault();
            });

	el = document.getElementById("overlay");
	el.style.visibility = (el.style.visibility === "visible") ? "hidden" : "visible";

        </script>
        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-51572429-1', 'aakosu.org');
            ga('require', 'displayfeatures');
            ga('send', 'pageview');

        </script>
    </body>
</html>
