INSERT INTO TBL_RECOMMENDED_MUSICAL (title, content, created_at, user_id) VALUES
                                                                              ('Les Misérables', '빅토르 위고의 소설을 바탕으로 한 작품으로, 프랑스 혁명 시대를 배경으로 합니다. 감동적인 스토리와 웅장한 음악으로 많은 사람들에게 사랑받는 뮤지컬입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('The Phantom of the Opera', '앤드루 로이드 웨버의 걸작으로, 파리 오페라하우스를 배경으로 한 사랑과 집착의 이야기를 다룬 뮤지컬입니다. 화려한 무대와 웅장한 음악이 매력적입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Wicked', '오즈의 마법사의 뒤에 숨겨진 이야기를 재해석한 작품입니다. 등장인물의 깊이와 무대 연출이 인상적인 이 뮤지컬은 마법과 우정, 차별에 대한 이야기를 담고 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Mamma Mia!', 'ABBA의 명곡들을 바탕으로 한 유쾌하고 밝은 뮤지컬입니다. 그리스의 한 섬에서 벌어지는 결혼식과 관련된 이야기를 그리며, 가벼운 마음으로 즐길 수 있는 작품입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('The Lion King', '디즈니의 애니메이션을 원작으로 한 작품으로, 화려한 의상과 안무가 돋보입니다. 애니메이션의 감동적인 스토리와 더불어 웅장한 무대가 볼거리입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Aladdin', '디즈니의 또 다른 히트작을 뮤지컬화한 작품입니다. 신비로운 무대 연출과 친숙한 음악들로 초보자들에게 쉽게 다가갈 수 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Rent', '90년대 뉴욕을 배경으로, 젊은 예술가들의 사랑과 삶, 고난을 다룬 작품입니다. 현대적인 감성과 록 음악이 인상적이며, 열정적인 분위기가 특징입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Hamilton', '미국 건국의 아버지 중 한 명인 알렉산더 해밀턴의 삶을 그린 뮤지컬로, 힙합, R&B 등 다양한 장르의 음악과 역동적인 무대가 돋보입니다. 최근 큰 인기를 끌고 있는 작품입니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Chicago', '1920년대 시카고를 배경으로 한 범죄와 쇼비즈니스의 세계를 다룬 뮤지컬입니다. 재즈 음악과 화려한 댄스, 블랙 코미디 요소가 잘 어우러져 있습니다.', CURRENT_TIMESTAMP, NULL),
                                                                              ('Billy Elliot', '영국 탄광촌의 소년 빌리가 발레를 배우며 겪는 성장기를 다룬 뮤지컬입니다. 감동적인 스토리와 열정적인 춤이 잘 어우러져 초보자들에게 추천하기 좋은 작품입니다.', CURRENT_TIMESTAMP, NULL);

INSERT INTO TBL_MUSICAL_TERMS (musical_terms, musical_terms_definition, created_at, user_id) VALUES
                                                                                                 ('넘버', '뮤지컬에 등장하는 모든 노래를 의미하며, 대본과 악보에서 각 노래에 번호가 붙어있는 것에서 유래되었습니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('앙상블', '주연 배우 외 여러 역할을 맡으며 무대를 채우는 배우들을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('티켓팅', '티켓을 구매하는 과정을 말합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('피켓팅', '매우 치열한 티켓팅을 뜻하는 은어입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('캐스팅', '뮤지컬 출연 배우를 선정하는 과정입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('인터미션', '1막과 2막 사이 휴식 시간을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오버추어', '뮤지컬 시작을 알리는 서곡입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('커튼콜', '공연 후 배우들이 등장해 관객의 박수를 받는 장면입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('마티네', '낮 시간에 열리는 공연을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오케스트라 피트', '오케스트라가 위치한 무대 아래 공간을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('프리뷰', '정식 공연 전 시범 공연을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('프레스콜', '언론을 대상으로 하는 공연 시연입니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('OP석', '오케스트라 피트 바로 앞 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('중블', '무대 중앙 구역에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('오블', '무대 오른쪽에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('왼블', '무대 왼쪽에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('극사', '좌석 끝에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('시야 제한석', '무대 일부가 보이지 않는 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL),
                                                                                                 ('하나님석', '극장의 가장 높은 층에 위치한 좌석을 의미합니다.', CURRENT_TIMESTAMP, NULL);

INSERT INTO TBL_ETIQUETTE (etiquette, etiquette_description, created_at)
VALUES
    ('공연 시작 전 도착하기', '공연 시작 10-15분 전에 도착하여 자리에 앉아 공연을 준비하는 것이 좋습니다.', CURRENT_TIMESTAMP),
    ('휴대폰 사용 금지', '공연 중에는 휴대폰을 꺼두거나 비행기 모드로 설정하여 방해가 되지 않도록 해야 합니다.', CURRENT_TIMESTAMP),
    ('음식과 음료', '공연 중에는 음식과 음료를 섭취하지 않는 것이 좋습니다. 관객과 배우 모두에 대한 예의입니다.', CURRENT_TIMESTAMP),
    ('조용히 관람하기', '공연 중에는 대화나 소리를 내지 말고 조용히 관람해야 합니다. 이는 다른 관객에 대한 예의입니다.', CURRENT_TIMESTAMP),
    ('박수와 환호', '좋은 장면 후에는 박수와 환호로 배우들에게 응원해 주세요. 이는 그들의 노력에 대한 감사의 표현입니다.', CURRENT_TIMESTAMP),
    ('중간 퇴장 자제', '가능하면 1막과 2막 사이의 인터미션 동안만 자리를 비우는 것이 좋습니다. 공연 중간에 퇴장하는 것은 예의에 어긋납니다.', CURRENT_TIMESTAMP),
    ('복장 규정 준수', '공연에 맞는 복장을 갖추는 것이 중요하며, 주최측의 복장 규정을 따르는 것이 좋습니다.', CURRENT_TIMESTAMP),
    ('자녀 동반 시 주의', '어린이를 동반할 경우, 공연에 적합한 나이인지 확인하고 관람 매너를 잘 지킬 수 있도록 지도해야 합니다.', CURRENT_TIMESTAMP);
