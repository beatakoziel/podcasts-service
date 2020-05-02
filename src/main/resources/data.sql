replace into podcast (id, title, description, category, image_url, audio_url, blocked)
values (1,
        'Pieniądze a władza',
        'Pierwszy tak dokładny podcast, który pokazuje słabości ludzi bogatych.',
        'money',
        'https://images.unsplash.com/photo-1527788263495-3518a5c1c42d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1083&q=80',
        '',
        false);
replace into podcast (id, title, description, category, image_url, audio_url, blocked)
values (2,
        'Jak zostać królem?',
        'Kolejny podcast z serii Jak zostać królem własnego losu?. Polecany dla amatorów sukcesu.',
        'mindset',
        'https://images.unsplash.com/photo-1578925518470-4def7a0f08bb?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1051&q=80',
        '',
        true);

replace into podcast_user(id, username, password) values (1, 'admin', '$2y$12$b5LIwLw1C39dptntq1atSu/A2YhCXYWxiPLou/yT5DgKtVaYji3TG');