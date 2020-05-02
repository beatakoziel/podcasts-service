ALTER TABLE podcast CHANGE COLUMN audio_url audio_url VARCHAR(1000);
replace into podcast (id, title, description, category, image_url, audio_url, blocked)
values (1,
        'Walka z kryzysem',
        'Jak radzić sobie finansowo podczas kryzysu, który w tym momencie już ma miejsce?',
        'money',
        'https://images.unsplash.com/photo-1527788263495-3518a5c1c42d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1083&q=80',
        'https://cdn.fbsbx.com/v/t59.3654-21/95702687_683683625776063_3745510557864689664_n.mp3/techniki_walki_z_kryzysem-mp3cut.net.mp3?_nc_cat=110&_nc_sid=7272a8&_nc_ohc=KYkpKtKBpoEAX_s7A9_&_nc_ht=cdn.fbsbx.com&oh=ac56aeca95278b8c1f6aeb15d8e3b85f&oe=5EAF2880&dl=1',
        false);
replace into podcast (id, title, description, category, image_url, audio_url, blocked)
values (2,
        'Metody zmiany nawyków',
        'W świecie rozwoju osobistego nawyki są największym narzędziem zmiany czegokolwiek.',
        'mindset',
        'https://images.unsplash.com/photo-1554415707-6e8cfc93fe23?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80',
        'https://cdn.fbsbx.com/v/t59.3654-21/95531980_931546813970036_8592316842855366656_n.mp3/metody_zmiany_nawykow.mp3?_nc_cat=107&_nc_sid=7272a8&_nc_ohc=Q6uchB9zqPwAX94oyFb&_nc_ht=cdn.fbsbx.com&oh=afa99bad9347d9023c5bb4df825cf924&oe=5EAEE89C&dl=1',
        true);

replace into podcast_user(id, username, password) values (1, 'admin', '$2y$12$b5LIwLw1C39dptntq1atSu/A2YhCXYWxiPLou/yT5DgKtVaYji3TG');