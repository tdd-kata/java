import pika
# python3 -m pip install pika==1.3.2
import json

# RabbitMQ 서버에 연결
connection = pika.BlockingConnection(pika.ConnectionParameters(
    host='localhost',        # RabbitMQ 서버 호스트
    port=5672,               # RabbitMQ 서버 포트
    virtual_host='/',        # 가상 호스트 (기본값은 '/')
    credentials=pika.PlainCredentials(
        username='admin',  # RabbitMQ 사용자 이름
        password='f2ca1bb6c7e907d06dafe4687e579fce76b37e4e93b7605022da52e6ccc26fd2'
    )
))

channel = connection.channel()

# 큐 선언 (이미 큐가 존재하는 경우, 이 단계를 생략할 수 있습니다)
queue_name = 'sample.queue'  # 큐 이름을 원하는 이름으로 변경
# channel.queue_declare(queue=queue_name, durable=True)  # 큐를 durable하게 선언 (메시지를 디스크에 저장)

# JSON 형식의 메시지 생성
message_data = {
  'name': 'mark',
  'age': 30,
}
message_json = json.dumps(message_data)

# 메시지 게시
channel.basic_publish(
    exchange='',
    routing_key=queue_name,  # 큐 이름을 라우팅 키로 사용
    body=message_json,
    properties=pika.BasicProperties(
        delivery_mode=2,  # 메시지를 영구 저장 (durable한 큐를 사용할 경우 필요)
    )
)

print(f"메시지 게시 완료: {message_json}")

# 연결 종료
connection.close()
