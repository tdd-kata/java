db.auth("root", "pass");

db = db.getSiblingDB("testlocal");

db.createUser({
  user: "user",
  pwd: "pass",
  roles: [{ role: "readWrite", db: "testlocal" }],
});
