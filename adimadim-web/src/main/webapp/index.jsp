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
        <link rel="stylesheet" href="/resource/css/foundation.css" />
        <link rel="stylesheet" href="/resource/css/main.css" />
        <script src="/resource/js/vendor/modernizr.js"></script>
    </head>
    <body class="antialiased no-bg">
       <hr class="gradient-bar" />
        <a href="" class="menu-toggle">
            <img src="/resource/image/hamburger.png" width="24" />    
        </a>

        <img src="/resource/image/aakosu-big-logo.png" alt="Adım Adım Koşu" class="cover-logo" />

        <ul class="example-orbit" data-orbit="" data-options="animation: fade; circular: true; slide_number: false; navigation_arrows: false; timer_show_progress_bar: false; timer_speed: 3000; pause_on_hover: false;">
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
            <li style="background-image: url('/resource/image/cover1.png');"></li>
            <li style="background-image: url('/resource/image/cover2.png');"></li>
            <li style="background-image: url('/resource/image/cover3.png');"></li>
        </ul>

        <nav class="cover">
            <div class="midway">
                <a href="/dagi/next-run.jsf">
                    <img src="/resource/image/run-icon.png" />
                    <span>Yarışlar</span>
                </a>
                <a href="/dagi/barcode.jsf">
                    <img src="/resource/image/barcode-icon.png" />
                    <span>Göğüs No</span>
                </a>
                <a href="/dagi/about.jsf">
                    <img src="/resource/image/about-icon.png" />
                    <span>Nedir?</span>
                </a>
                <a href="/dagi/adimadim.jsf">
                    <img src="/resource/image/aa-icon2.png" class="aa" />
                    <span>Adım Adım</span>
                </a>
                <a href="/dagi/location.jsf">
                    <img src="/resource/image/location-icon.png" />
                    <span>Nerede?</span>
                </a>
                <a href="/dagi/join.jsf">
                    <img src="/resource/image/join-icon.png" />
                    <span>Kayıt Ol</span>
                </a>
                <a href="/dagi/faq.jsf">
                    <img src="/resource/image/faq-icon.png" />
                    <span>Sorular</span>    
                </a>    
            </div>
        </nav>
        <script src="/resource/js/vendor/jquery.js"></script>
        <script src="/resource/js/foundation.min.js"></script>
        <script src="/resource/js/vendor/midway.min.js"></script>
        <script>
            $(document).foundation();
            $('a.menu-toggle').on('click', function(e) {
                $('nav.cover').fadeToggle();
                e.preventDefault();
            });
        </script>
    </body>
</html>
