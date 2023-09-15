# python3 -m venv venv
# source venv/bin/activate
# pip install fastapi uvicorn
# uvicorn test_server:app --reload --port 38080
import time
from fastapi import FastAPI

app = FastAPI()


@app.post("/hello")
def read_hello():
    time.sleep(2)
    return {"message": "hi"}
