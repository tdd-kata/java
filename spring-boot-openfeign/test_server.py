# python3 -m venv venv
# source venv/bin/activate
# pip install fastapi uvicorn
# uvicorn test_server:app --reload --port 38080
import time
from fastapi import FastAPI
from pydantic import BaseModel


class Person(BaseModel):
    name: str
    age: int


app = FastAPI()


@app.post("/hello")
def read_hello(friend: Person):
    time.sleep(2)
    return {"message": f"hi {friend.name}, you are {friend.age}"}
