alter table podcast CHANGE COLUMN audio_url audio_url VARCHAR(1000);
insert into podcast (id, title, description, category, image_url, audio_url, blocked, length)
values (1,
        'Walka z kryzysem',
        'Jak radzić sobie finansowo podczas kryzysu, który w tym momencie już ma miejsce?',
        'money',
        'https://images.unsplash.com/photo-1527788263495-3518a5c1c42d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1083&q=80',
        'metody_zmiany_nawykow',
        false,
        '04:06') ON DUPLICATE KEY UPDATE title='Walka z kryzysem';
insert into podcast (id, title, description, category, image_url, audio_url, blocked, price, length)
values (2,
        'Metody zmiany nawyków',
        'W świecie rozwoju osobistego nawyki są największym narzędziem zmiany czegokolwiek.',
        'mindset',
        'https://images.unsplash.com/photo-1554415707-6e8cfc93fe23?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80',
        'metody_zmiany_nawykow',
        true,
        5,
        '16:24') ON DUPLICATE KEY update title='Metody zmiany nawyków';
insert into podcast (id, title, description, category, image_url, audio_url, blocked, price, length)
values (3,
        'Jaką cenę zapłacimy',
        'O globalnym ociepleniu opowiada fizyk i ekspert ds. zmian klimatycznych Marcin Popkiewicz.',
        'ecology',
        'https://images.unsplash.com/photo-1517925035435-7976539b920d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1055&q=80',
        'globalne_ocieplenie',
        true,
        1,
        '12:01') ON DUPLICATE KEY update title='Jaką cenę zapłacimy';
insert into podcast (id, title, description, category, image_url, audio_url, blocked, price, length)
values (4,
        'Książkowy rozum',
        'Jak książki kreują nasze spojrzenie na świat? Przewaga książek nad telewizją?',
        'mindset',
        'https://images.unsplash.com/photo-1512474331201-782fc6a4ee29?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80',
        'metody_zmiany_nawykow',
        true,
        2,
        '11:11') ON DUPLICATE KEY update title='Książkowy rozum';
insert into podcast (id, title, description, category, image_url, audio_url, blocked, price, length)
values (5,
        'Znajdź sobie hobby',
        'O tym jak ważne jest hobby w rozwoju samego siebie i relacjach z innymi..',
        'mindset',
        'https://images.unsplash.com/photo-1522410818928-5522dacd5066?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80',
        'metody_zmiany_nawykow',
        true,
        2,
        '12:00') ON DUPLICATE KEY update title='Znajdź sobie hobby';

replace into podcast_user(id, username, password) values (1, 'admin', '$2y$12$b5LIwLw1C39dptntq1atSu/A2YhCXYWxiPLou/yT5DgKtVaYji3TG');